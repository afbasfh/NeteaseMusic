package com.example.login

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.lib_common_ui.AnimUtils
import org.sufficientlysecure.htmltextview.HtmlTextView


/***
 * 给协议视图绑定一个属性agreementShouldSwing
 */
@BindingAdapter("swing","IgViewModel", requireAll = true)
fun View.shouldSwing(swing:Boolean,viewModel: LgLoginViewModel){
    if (swing){
        AnimUtils.startSwingAnimation(this)
        viewModel.agreementShouldSwing.postValue(false)
    }
}
@BindingAdapter("htmlText")
fun View.htmlText(text:String){
    val htmlTextView = this as HtmlTextView
    val p1=text.substring(0,7)
    val p2=text.substring(7)
    val colorString="<font color=#507AAC>$p2</font>"
    htmlTextView.setHtml("$p1$colorString")
}

@BindingAdapter("initSrc")
fun View.initSrc(checked:Boolean){
    val iv = this as ImageView
    if (checked){
        iv.setImageResource(R.drawable.ic_checked)
    }else{
        iv.setImageResource(R.drawable.ic_check)
    }
}