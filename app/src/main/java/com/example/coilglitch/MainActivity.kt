@file:Suppress("FunctionName")

package com.example.coilglitch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.compose.AsyncImage
import coil.load

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val fragmentA = TestFragment()
        val fragmentB = TestFragment()

        findViewById<Button>(R.id.buttonA).setOnClickListener {
            setFragment(fragmentA)
        }

        findViewById<Button>(R.id.buttonB).setOnClickListener {
            setFragment(fragmentB)
        }

        setFragment(fragmentA)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}


class TestFragment : Fragment(R.layout.test_fragment) {

    private val adapter = TestAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recycler_view).adapter = adapter
    }
}


class TestAdapter : Adapter<ViewHolder>() {

    private val url = "https://picsum.photos/id/10/200"

    override fun getItemCount() = 2

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            0 -> ComposeView(parent.context)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        }

        return object : ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.bindCompose()
            1 -> holder.bindXml()
        }
    }

    private fun ViewHolder.bindCompose() {
        (itemView as ComposeView).setContent { TestItem(url) }
    }

    private fun ViewHolder.bindXml() {
        itemView.findViewById<ImageView>(R.id.image).load(url)
    }
}

@Composable
private fun TestItem(url: String) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .width(80.dp)
        ) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Gray),
            )
            Text(
                text = "Compose",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
