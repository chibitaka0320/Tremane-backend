package com.chibitaka.tremane_backend.common.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import com.chibitaka.tremane_backend.entity.UserGoalEntity;
import com.chibitaka.tremane_backend.entity.UserProfileEntity;

/** 計算用ユーティリティクラス */
public class Calc {

    /** 年齢計算 */
    public static Integer getAge(LocalDate birthday) {
        if (birthday == null) {
            return null;
        }
        LocalDate today = LocalDate.now();
        return Period.between(birthday, today).getYears();
    }

    /** 基礎代謝計算 */
    public static Integer getBmr(Integer gender, Double height, Double weight, Integer age) {
        if (gender == null || height == null || weight == null) {
            return null;
        }

        Integer bmr;
        if (gender == 0) {
            bmr = (int) ((13.397 * weight) + (4.799 * height) - (5.677 * age) + 88.362);
        } else if (gender == 1) {
            bmr = (int) ((9.247 * weight) + (3.098 * height) - (4.33 * age) + 447.593);
        } else {
            bmr = (int) (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age));
        }

        return bmr;
    }

    /** 総消費カロリー計算 */
    public static Integer getTotalCalorie(Integer bmr, Integer activeLevel) {
        if (bmr == null || activeLevel == null) {
            return null;
        }

        Integer totalCalorie;
        if (activeLevel == 0) {
            totalCalorie = (int) (bmr * 1.2);
        } else if (activeLevel == 1) {
            totalCalorie = (int) (bmr * 1.5);
        } else if (activeLevel == 2) {
            totalCalorie = (int) (bmr * 1.9);
        } else {
            totalCalorie = bmr;
        }
        return totalCalorie;
    }

    /** 目標摂取カロリー算出 */
    public static Integer getGoalCalorie(UserProfileEntity profile, UserGoalEntity goal) {
        try {
            // 基礎情報取得
            Integer age = getAge(profile.getBirthday());
            Integer bmr = getBmr(profile.getGender(), profile.getHeight(), profile.getWeight(), age);
            Integer totalCalorie = getTotalCalorie(bmr, profile.getActiveLevel());

            // 目標消費カロリー算出
            Double lossWeight = goal.getWeight() - goal.getGoalWeight();
            long days = ChronoUnit.DAYS.between(goal.getStart(), goal.getFinish());
            Double lossCalorie = (lossWeight * 7200) / days;

            return (int) (totalCalorie - lossCalorie);
        } catch (NullPointerException e) {
            return null;
        }

    }
}
