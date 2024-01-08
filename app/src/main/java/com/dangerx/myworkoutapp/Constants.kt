package com.dangerx.myworkoutapp

object Constants {

    fun defaultExercise(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1,
            "Jumping jacks",
            R.drawable.jacks_ok,
            false,
            false
        )
        exerciseList.add(jumpingJacks)
        val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.wall_s, false, false)
        exerciseList.add(wallSit)

        val pushUp = ExerciseModel(3, "Push Up", R.drawable.push_up, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            ExerciseModel(4, "abdominal Crunch", R.drawable.crunch, false, false)
        exerciseList.add(abdominalCrunch)

        val squats = ExerciseModel(5, "squats", R.drawable.squats, false, false)
        exerciseList.add(squats)

        val plank = ExerciseModel(6, "plank", R.drawable.plank, false, false)
        exerciseList.add(plank)

        val tricepDip = ExerciseModel(7, "tricepDip", R.drawable.tricep_dip, false, false)
        exerciseList.add(tricepDip)

        val highKnees = ExerciseModel(8, "highKnees", R.drawable.high_knees, false, false)
        exerciseList.add(highKnees)

        val pushRot = ExerciseModel(9, "PushRot", R.drawable.push_rot, false, false)
        exerciseList.add(pushRot)

        val lunges = ExerciseModel(10, "lunges", R.drawable.lunges, false, false)
        exerciseList.add(lunges)

        val situp = ExerciseModel(11, "situp", R.drawable.sit_up, false, false)
        exerciseList.add(situp)


        return exerciseList


    }


}