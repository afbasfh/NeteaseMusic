
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

/***
 * 封装supportFragment切换事件
 */
val Fragment.NAVIGATE_REPLACE: Int
    get() = 0
val Fragment.NAVIGATE_ADD: Int
    get() = 1

fun Fragment.navigate(
    @IdRes containerId: Int,
    fragment: Fragment,
    navigateType: Int = NAVIGATE_REPLACE,
    addToStack:Boolean=false,
    @AnimRes enter:Int=0,
    @AnimRes exit:Int=0,
    @AnimRes popEnter:Int=0,
    @AnimRes popExit:Int=0
) {
    requireActivity().supportFragmentManager.commit {
        setCustomAnimations(enter,exit, popEnter, popExit)
        if (navigateType == NAVIGATE_REPLACE) {
            replace(containerId, fragment)
        }
        if (addToStack){
            addToBackStack(null)
        }
    }
}