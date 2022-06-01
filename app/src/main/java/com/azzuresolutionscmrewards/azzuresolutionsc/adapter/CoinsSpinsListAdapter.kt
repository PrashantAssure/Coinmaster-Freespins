package com.azzuresolutionscmrewards.azzuresolutionsc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azzuresolutionscmrewards.azzuresolutionsc.databinding.LayListRowBinding
import com.azzuresolutionscmrewards.azzuresolutionsc.model.RewardResponse

class CoinsSpinsListAdapter(
    private val coinSpinsList: ArrayList<RewardResponse>
) :
    RecyclerView.Adapter<CoinsSpinsListAdapter.ViewHolder>() {
    lateinit var onClick: OnClick

    interface OnClick {
        fun onItemClick(rewardResponse: RewardResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coinSpinsList[position])
    }

    override fun getItemCount() = coinSpinsList.size

    inner class ViewHolder(private val binding: LayListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rewardResponse: RewardResponse) {
            binding.tvCount.text = rewardResponse.cm_title
            binding.tvDate.text = rewardResponse.cm_date
            binding.root.setOnClickListener {
                onClick.onItemClick(rewardResponse)
            }
        }
    }
}