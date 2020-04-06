//package com.example.pogodynka
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.util.DisplayMetrics
//import kotlin.math.roundToInt
//
//class Pop: Activity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.add_layout)
//        val dm  = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(dm)
//        val width   = dm.widthPixels
//        val height = dm.heightPixels
//        window.setLayout(width*.8.roundToInt(),height*.6.roundToInt())
//
//
//
//    }
//    companion object {
//
//        private val INTENT_USER_ID = "user_id"
//
//        fun newIntent(context: Context): Intent {
//            val intent = Intent(context, Pop::class.java)
//
//
//            return intent
//        }
//    }
//}
////Fragment
///*
//override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//): View? {
//
//
//    val root = inflater.inflate(R.layout.add_layout, container, false)
//    return root
//}
//
//companion object {
//
//    private val INTENT_USER_ID = "user_id"
//
//    fun newIntent(context: Context): Intent {
//        val intent = Intent(context, Pop::class.java)
//
//
//        return intent
//    }
//}
//}*/
