package com.rickon.ximalayakotlin.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.rickon.ximalayakotlin.R
import com.rickon.ximalayakotlin.util.GlobalUtil
import com.ximalaya.ting.android.opensdk.model.track.Track

/**
 * @Description:声音列表适配器
 * @Author:      高烁
 * @CreateDate:  2019-07-26 18:23
 * @Email:       gaoshuo521@foxmail.com
 */
class TrackAdapter : RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private var mContext: Context
    private var trackList: List<Track>
    private var showImageCover = false
    private lateinit var itemClickListener: IKotlinItemClickListener

    constructor(mContext: Context, list: List<Track>, showImageCover: Boolean) {
        this.mContext = mContext
        this.trackList = list
        this.showImageCover = showImageCover
    }

    class ViewHolder(var trackItemView: View) : RecyclerView.ViewHolder(trackItemView) {
        var imageCover: ImageView = trackItemView.findViewById(R.id.id_image_cover)
        var textViewTitle: TextView = trackItemView.findViewById(R.id.id_title_tv)
        var textViewPlayCount: TextView = trackItemView.findViewById(R.id.id_play_count_tv)
        var textViewDuration: TextView = trackItemView.findViewById(R.id.id_duration_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.track_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = trackList[position]
        with(item){
            holder.textViewTitle.text = trackTitle
            holder.textViewPlayCount.text = GlobalUtil.formatNum(playCount.toString(), false)
            holder.textViewDuration.text = DateUtils.formatElapsedTime(duration.toLong())
            if (showImageCover) {
                Glide.with(mContext)
                        .load(coverUrlLarge)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
                        .into(holder.imageCover)
            }
        }

        holder.trackItemView.setOnClickListener {
            itemClickListener.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    // 提供set方法
    fun setOnKotlinItemClickListener(itemClickListener: IKotlinItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    //自定义接口
    interface IKotlinItemClickListener {
        fun onItemClickListener(position: Int)
    }
}