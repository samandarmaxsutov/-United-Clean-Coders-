package uz.itmade.agrodatacollector.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.itmade.agrodatacollector.R
import uz.itmade.agrodatacollector.data.ImageData
import java.util.ArrayList

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.Holder>() {
    private var isSortedAscending = true
    var data= ArrayList<ImageData>()
    private var onClickListener:((ImageData)->Unit)?=null
    fun onClickItem(block:(ImageData)->Unit){
        onClickListener=block
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newsItems:List<ImageData>){
        data.clear()
        data.addAll(newsItems)
        this.notifyDataSetChanged()

    }
    inner class Holder(view: View): RecyclerView.ViewHolder(view){



        private val image: ImageView =view.findViewById(R.id.imageView)

        private val text: TextView = view.findViewById(R.id.textViewTitle)




        @SuppressLint("SetTextI18n")
        fun bind(){
            val item=data[adapterPosition]

            text.text=item.description
            Glide
                .with(image)
                .load(item.imageLink)
                .centerCrop()
                .placeholder(R.drawable.img)
                .into(image);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(LayoutInflater.from(parent.context).inflate(R.layout.image_item,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =holder.bind()

    override fun getItemCount()=data.size




}