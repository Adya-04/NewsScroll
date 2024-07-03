package com.example.newsscroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.newsscroll.databinding.ActivityMainBinding
import com.example.newsscroll.databinding.ItemNewsBinding

class NewsListAdapter( private val listener:NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

      private val items: ArrayList<News> = ArrayList()

      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            // in below layoutinflater has been used to convert the xml to view
            //since its return type is new view holder thats why we re returning view holder
            //attachToRoot: This boolean parameter indicates whether the inflated view should be attached to the parent immediately:
            //false: The inflated view will not be attached to the parent automatically. This is often used in RecyclerView adapters because the RecyclerView will handle attaching the view to the parent.
            //true: The view will be attached to the parent during inflation. This is less common in scenarios like RecyclerView adapters.
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
            val viewHolder = NewsViewHolder(view)
            view.setOnClickListener{
                    listener.onItemClicked(items[viewHolder.adapterPosition])
            }
            return  viewHolder
      }

      override fun getItemCount(): Int {
            return  items.size
      }
      //yeh b smj ni aya niche wla
      override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            val currentItem = items[position]
            holder.titleview.text = currentItem.title
            holder.author.text = currentItem.author
            Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)

      }
      fun updateNews(updatedNews : ArrayList<News>){
            items.clear()
            items.addAll(updatedNews)

            notifyDataSetChanged()
      }

}
//How to use binding in below lines
class NewsViewHolder(itemView: View) : ViewHolder(itemView){
      val titleview : TextView = itemView.findViewById(R.id.title)
      val image: ImageView = itemView.findViewById(R.id.image)
      val author: TextView = itemView.findViewById(R.id.author)
}

//Creating callback from adapter to main activity(to tell activity that an item has been clicked) by using interface
interface NewsItemClicked{
      fun onItemClicked(item : News)
}