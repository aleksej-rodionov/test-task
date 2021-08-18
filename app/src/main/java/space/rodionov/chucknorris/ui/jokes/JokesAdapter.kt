package space.rodionov.chucknorris.ui.jokes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import space.rodionov.chucknorris.data.Joke
import space.rodionov.chucknorris.databinding.ItemJokeBinding

class JokesAdapter(

) : ListAdapter<Joke, JokesAdapter.JokeViewHolder>(JokeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = ItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val curJoke = getItem(position)
        holder.bind(curJoke)
    }

    inner class JokeViewHolder(private val binding: ItemJokeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(joke: Joke) {
            binding.tvNote.text = joke.text
        }
    }

    class JokeComparator : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke) =
            oldItem.text == newItem.text
    }
}