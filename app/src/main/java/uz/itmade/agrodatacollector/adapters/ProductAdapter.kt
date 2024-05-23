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
import uz.itmade.agrodatacollector.data.ProductData
import java.util.ArrayList

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.Holder>() {
    private var isSortedAscending = true
    var data= ArrayList<ProductData>()
    private var onClickListener:((ProductData)->Unit)?=null
    fun onClickItem(block:(ProductData)->Unit){
        onClickListener=block
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(newsItems:List<ProductData>){
        data.clear()
        data.addAll(newsItems)
        this.notifyDataSetChanged()

    }
    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val cardView: CardView = view.findViewById(R.id.card_view)
        private val name: TextView =view.findViewById(R.id.product_name)

        private val image: ImageView =view.findViewById(R.id.product_image)

        private val category: TextView = view.findViewById(R.id.product_category)
        private val price: TextView = view.findViewById(R.id.product_price)

        init {
            cardView.setOnClickListener {
                onClickListener?.invoke(data[adapterPosition])
            }

        }

        @SuppressLint("SetTextI18n")
        fun bind(){
            val item=data[adapterPosition]
            name.text=item.name

            price.text = "$${item.price}"
            Log.d("Image",item.imageUrl)
            Glide
                .with(image)
                .load(item.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.img)
                .into(image);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return  Holder(LayoutInflater.from(parent.context).inflate(R.layout.product_card,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =holder.bind()

    override fun getItemCount()=data.size



    @SuppressLint("NotifyDataSetChanged")
    fun sortByPrice() {
        if (isSortedAscending) {
            data.sortByDescending { it.price }
        } else {
            data.sortBy { it.price }
        }
        isSortedAscending = !isSortedAscending
        this.notifyDataSetChanged();
    }

}