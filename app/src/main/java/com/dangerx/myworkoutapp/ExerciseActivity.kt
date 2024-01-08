package com.dangerx.myworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerx.myworkoutapp.databinding.ActivityExerciseAcitivityBinding
import com.dangerx.myworkoutapp.databinding.DialogCustomBackConfirmationBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding : ActivityExerciseAcitivityBinding? = null

    private var restTimer:CountDownTimer? = null
    private var restProgress = 0
    private var restTimerduration: Long = 1
    private var exerciseTimer:CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimeDuration: Long = 1

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

   private var exerciseAdapter : ExerciseStatusAdapter? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseAcitivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()


        }

        exerciseList = Constants.defaultExercise()

        tts = TextToSpeech(this, this)




        setupRestView()

        SetupExerciseStatusRecyclerView()

    }

    override fun onBackPressed (){
        customDialogForBackButton()
        super.onBackPressed()
    }



     private fun customDialogForBackButton() {
         val customDialog = Dialog(this)
         val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
         customDialog.setContentView(dialogBinding.root)
         customDialog.setCanceledOnTouchOutside(false)
         dialogBinding.btnYes.setOnClickListener {
             this@ExerciseActivity.finish()
             customDialog.dismiss()

         }
         dialogBinding.btnNo.setOnClickListener {
             customDialog.dismiss()

         }
         customDialog.show()
     }






    private fun SetupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter

    }





    private fun setupRestView(){


        try{
            val  soundURI = Uri.parse("android.resource://com.dangerx.myworkoutapp/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping = false
            player?.start()
        }catch (e:Exception) {
            e.printStackTrace()
        }

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTittle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpComingLabel?.visibility = View.VISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.VISIBLE




        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpComingExerciseName?.text = exerciseList!![currentExercisePosition +1].getName()


        setRestProgressBar()


    }

    private fun setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTittle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpComingLabel?.visibility = View.INVISIBLE
        binding?.tvUpComingExerciseName?.visibility = View.INVISIBLE

        if(exerciseTimer !=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())


        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()



    }



    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(restTimerduration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {


                currentExercisePosition++


                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()



                setupExerciseView()


            }

        }.start()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(exerciseTimeDuration*1000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {






                if(currentExercisePosition <exerciseList?.size!! -1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)

                }



            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()


        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
         if(exerciseTimer != null){
             exerciseTimer?.cancel()
             exerciseProgress=0
         }

        if (tts !=null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if (player !=null) {
            player!!.stop()
        }

        binding = null
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            val result = tts?.setLanguage(Locale.US)

            if (result== TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }

        } else{
            Log.e("TTS","Initialization Failed!")
        }




    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }



}