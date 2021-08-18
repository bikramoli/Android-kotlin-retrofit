package com.example.myapplication



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.example.myapplication.retrofit.IMyservice
import com.example.myapplication.retrofit.RetrofitClient
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.rengwuxian.materialedittext.MaterialEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    lateinit var  iMyservice: IMyservice
    internal  var compositeDisposable = CompositeDisposable()

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init API
        val retrofit = RetrofitClient.getInstance()
        iMyservice = retrofit.create(IMyservice::class.java)

        //event
        btnLogin.setOnClickListener{
            loginUser(etLEmail.text.toString(), etLPassword.text.toString())
        }
        btnT.setOnClickListener{
         val itemView= LayoutInflater.from(this@MainActivity)
             .inflate(R.layout.register_layout, null)

            MaterialStyledDialog.Builder(this@MainActivity)

                .setTitle("Registration")
                .setDescription("Please fill all fields")
                .setCustomView(itemView)
                .setNegativeText("CANCEL")
                .onNegative{dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveText("REGISTER")
                .onPositive(
                        MaterialDialog.SingleButtonCallback{_,_->

                    val edt_fullname = itemView.findViewById<View>(R.id.etfullname) as EditText
                    val edt_email = itemView.findViewById<View>(R.id.etEmail) as EditText
                    val edt_mobile = itemView.findViewById<View>(R.id.etMobile) as EditText
                    val edt_address = itemView.findViewById<View>(R.id.etAddress) as EditText
                    val edt_password = itemView.findViewById<View>(R.id.etPassword) as EditText

                    if(TextUtils.isEmpty(edt_fullname.text.toString()))
                    {
                        Toast.makeText(this@MainActivity, "Name cannot be null or empty",Toast.LENGTH_SHORT).show()
                        return@SingleButtonCallback;
                    }
                            if(TextUtils.isEmpty(edt_email.text.toString()))
                            {
                                Toast.makeText(this@MainActivity, "Email cannot be null or empty",Toast.LENGTH_SHORT).show()
                                return@SingleButtonCallback;
                            }
                            if(TextUtils.isEmpty(edt_mobile.text.toString()))
                            {
                                Toast.makeText(this@MainActivity, "Mobile cannot be null or empty",Toast.LENGTH_SHORT).show()
                                return@SingleButtonCallback;
                            }
                            if(TextUtils.isEmpty(edt_address.text.toString()))
                            {
                                Toast.makeText(this@MainActivity, "Address cannot be null or empty",Toast.LENGTH_SHORT).show()
                                return@SingleButtonCallback;
                            }
                            if(TextUtils.isEmpty(edt_password.text.toString()))
                            {
                                Toast.makeText(this@MainActivity, "Password cannot be null or empty",Toast.LENGTH_SHORT).show()
                                return@SingleButtonCallback;
                            }

                            registerUser(edt_fullname.text.toString(),
                            edt_email.text.toString(),
                                edt_mobile.text.toString(),
                                edt_address.text.toString(),
                                edt_password.text.toString()
                                )
                }).show()
        }

    }

    private fun registerUser(fullName: String, email: String, mobile: String, address: String, password: String) {
        compositeDisposable.add(iMyservice.registerUser(fullName, email, mobile, address, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                    result -> Toast.makeText(this@MainActivity, ""+result,Toast.LENGTH_SHORT).show()
            }
        )

    }

    private fun loginUser(email: String, password: String) {

        //check empty email
        if (TextUtils.isEmpty(email)){
            Toast.makeText(this@MainActivity, "Email cannot be null or empty", Toast.LENGTH_SHORT).show()
            return;
        }
        //check empty password
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this@MainActivity, "Password cannot be null or empty", Toast.LENGTH_SHORT).show()
            return;
        }

        compositeDisposable.add(iMyservice.loginUser(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                result -> Toast.makeText(this@MainActivity, "Login successfull"+result,Toast.LENGTH_SHORT).show()
            }
        )
    }
}