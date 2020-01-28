package br.com.sigga.blog.fragments

import br.com.sigga.blog.activities.MainActivity


open class MainBaseFragment : BaseFragment() {

    protected val mainActivity: MainActivity? by lazy { activity as? MainActivity }

}
