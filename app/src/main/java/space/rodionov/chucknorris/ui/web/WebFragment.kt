package space.rodionov.chucknorris.ui.web

import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import space.rodionov.chucknorris.R
import space.rodionov.chucknorris.databinding.FragmentWebBinding

class WebFragment : Fragment(R.layout.fragment_web) {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WebViewModel by viewModels()

    private val wvClient = WebViewClient()

    private lateinit var browseLine: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWebBinding.bind(view)

        binding.apply {
            webView.webViewClient = wvClient
            webView.webViewClient = object : WebViewClient() {
                override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                    viewModel.setCurUrl(url)
                    viewModel.setInputUrl(url)
                    webView.requestFocus()
                    super.doUpdateVisitedHistory(view, url, isReload)
                }
            }

            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.urlFlow.collect {
                    val curUrl = it ?: return@collect
                    webView.loadUrl(curUrl)
                }
            }

            webView.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_BACK) {
                        if (webView != null) {
                            if (webView.canGoBack()) {
                                webView.goBack()
                            } else {
                                activity?.let {
                                    it.onBackPressed()
                                }
                            }
                        }
                    }
                }
                true
            }
        }

        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_menu, menu)

        val browseItem = menu.findItem(R.id.action_browse)
        browseLine = browseItem.actionView as SearchView

        val inputUrl = viewModel.inputUrl.value

        if (inputUrl != null && inputUrl.isNotEmpty()) {
            browseItem.expandActionView()
            browseLine.setQuery(inputUrl, false)
        }

        browseLine.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.submitUrl()
                browseLine.clearFocus()
                binding.webView.requestFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.inputUrl.value = newText
                return true
            }

        })

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.urlFlow.collect {
                val curUrl = it ?: return@collect
                browseLine.setQuery(curUrl, false)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        browseLine.setOnQueryTextListener(null)
        _binding = null
    }
}





