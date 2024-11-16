package no.realitylab.arface

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

var Mustache = R.drawable.mustache
var MakeUp = R.drawable.makeup

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val repo = SettingsRepo(this)

        if (repo.mustacheBool()){
            anotherMustache.setText("Сделать усы коричневыми")
            Mustache = R.drawable.mustache
        }else{
            anotherMustache.setText("Сделать усы черными")
            Mustache = R.drawable.mustache2
        }
        if (repo.makeUpBool()){
            anotherMakeUp.setText("Сделать губы синими")
            MakeUp = R.drawable.makeup
        }else{
            anotherMakeUp.setText("Сделать губы красными")
            MakeUp = R.drawable.makeup2
        }

        anotherMustache.setOnClickListener {
            if (repo.mustacheBool()) {
                Mustache = R.drawable.mustache2
                anotherMustache.setText("Сделать усы черными")
                Toast.makeText(applicationContext, "Коричневые усы!", Toast.LENGTH_SHORT).show()
            }
            else {
                Mustache = R.drawable.mustache
                anotherMustache.setText("Сделать усы коричневыми")
                Toast.makeText(applicationContext, "Черные усы!", Toast.LENGTH_SHORT).show()
            }
            repo.switchMustacheBool()
        }

        anotherMakeUp.setOnClickListener {
            if (repo.makeUpBool()){
                MakeUp = R.drawable.makeup2
                anotherMakeUp.setText("Сделать губы красными")
                Toast.makeText(applicationContext, "Синии губы!", Toast.LENGTH_SHORT).show()
            }
            else{
                MakeUp = R.drawable.makeup
                anotherMakeUp.setText("Сделать губы синими")
                Toast.makeText(applicationContext, "Красные губы!", Toast.LENGTH_SHORT).show()
            }
            repo.switchMakeUpBool()
        }

        goToBack.setOnClickListener {
            finish()
        }
    }
}