# 本番（EC2）デプロイ手順

対象: `tremane-backend` を本番EC2インスタンスへデプロイする際の手順。

## 前提構成

- EC2上では Docker を使わず、`systemd` が jar を直接実行している
- サービス名: `tremane.service`
- ユニットファイル: `/etc/systemd/system/tremane.service`
- 実行コマンド: `/usr/bin/java -jar /home/ec2-user/app/tremane-app.jar`
- 環境変数ファイル: `/etc/tremane-backend.env`（`EnvironmentFile=`で読み込み）
- Firebaseサービスアカウントキー: `/etc/secrets/serviceAccountKey.json`（EC2上に事前配置。デプロイの都度の作業は不要）

```ini
[Unit]
Description=Spring Boot App Tremane
After=network.target

[Service]
User=ec2-user
WorkingDirectory=/home/ec2-user/app
ExecStart=/usr/bin/java -jar /home/ec2-user/app/tremane-app.jar
SuccessExitStatus=143
Restart=always
RestartSec=10
StandardOutput=journal
StandardError=journal
EnvironmentFile=/etc/tremane-backend.env

[Install]
WantedBy=multi-user.target
```

## デプロイ手順

### 1. ローカルで最新の jar をビルド

```bash
cd tremane-backend
./mvnw clean package -DskipTests
```

`pom.xml` の `finalName` 設定により `target/tremane-app.jar` が生成される。

### 2. EC2へ jar を転送（別名で転送してから切り替える）

```bash
scp -i ~/tremane-prd.pem target/tremane-app.jar ec2-user@ec2-13-114-226-131.ap-northeast-1.compute.amazonaws.com:/home/ec2-user/app/tremane-app.new.jar
```

### 3. EC2にSSHして切り替え・再起動

```bash
ssh -i ~/tremane-prd.pem ec2-user@ec2-13-114-226-131.ap-northeast-1.compute.amazonaws.com
```

EC2上で:

```bash
cd /home/ec2-user/app

# ロールバック用に旧jarをバックアップ
cp tremane-app.jar tremane-app.jar.bak.$(date +%Y%m%d%H%M%S)

# 新jarに差し替え
mv tremane-app.new.jar tremane-app.jar

# 新しい環境変数が必要な変更の場合、先に追記されているか確認
sudo cat /etc/tremane-backend.env

# サービス再起動
sudo systemctl restart tremane.service
```

### 4. 起動確認

```bash
sudo systemctl status tremane.service
sudo journalctl -u tremane.service -n 50 --no-pager
```

`active (running)` になっていて、エラーログが出ていなければOK。

### 5. 動作確認

該当機能（新規登録・パスワードリセット等、変更内容に応じて）を実際にアプリから1件試し、想定通り動作することを確認する。

## ロールバック手順

```bash
cd /home/ec2-user/app
cp tremane-app.jar.bak.<バックアップ時刻のタイムスタンプ> tremane-app.jar
sudo systemctl restart tremane.service
```

DBスキーマ変更を伴わない変更であれば、jarの差し替えだけで安全に戻せる。

## 新しい環境変数・Secretを追加する場合

`/etc/tremane-backend.env` に1行追記する。

```bash
echo 'KEY=VALUE' | sudo tee -a /etc/tremane-backend.env
```

編集後は `sudo cat /etc/tremane-backend.env` で内容を確認し、`systemctl restart tremane.service` で反映する（envファイルの変更だけではプロセスに反映されないため、必ず再起動が必要）。

## トラブルシュート時の調査コマンド

```bash
# サービス一覧からtremane関連を確認
systemctl list-units --type=service --all | grep -i tremane

# ユニットファイルの場所確認
systemctl show tremane.service -p FragmentPath

# Firebaseサービスアカウントキーの配置確認
sudo find / -maxdepth 4 -iname "serviceAccountKey.json" 2>/dev/null
```
