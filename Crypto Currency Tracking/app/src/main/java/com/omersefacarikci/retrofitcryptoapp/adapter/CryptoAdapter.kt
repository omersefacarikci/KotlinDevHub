package com.omersefacarikci.retrofitcryptoapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.omersefacarikci.retrofitcryptoapp.R
import com.omersefacarikci.retrofitcryptoapp.modal.CryptoModel


class CryptoAdapter(private val crptoList : ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<CryptoAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }
    private val colors : Array<String> = arrayOf("#666666","#777777","#515151")
    class RowHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cryptoModel : CryptoModel, colors: Array<String>,position: Int,listener: Listener){
            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            val textName = itemView.findViewById<TextView>(R.id.textname)
            textName.text = cryptoModel.currency
            val textprice = itemView.findViewById<TextView>(R.id.textprice)
            textprice.text = cryptoModel.price
            val background = itemView.setBackgroundColor(Color.parseColor(colors[position % colors.size]))
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(
        holder: RowHolder,
        position: Int
    ) {
        holder.bind(crptoList[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return crptoList.size
    }


}