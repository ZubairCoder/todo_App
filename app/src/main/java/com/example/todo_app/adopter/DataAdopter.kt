package com.example.todo_app.adopter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.databinding.RecyclerLayoutBinding
import com.example.todo_app.db.Todo
import com.example.todo_app.interfaces.onItemClick
import com.example.todo_app.viewModel.TodoViewModel

class DataAdopter(var todoViewModel: TodoViewModel, var context: Context, val data : List<Todo>, val onclick: onItemClick) : RecyclerView.Adapter<DataAdopter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdopter.MyViewHolder {
        val binding = RecyclerLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding,onclick)
    }

    override fun onBindViewHolder(holder: DataAdopter.MyViewHolder, position: Int) {
        val data = data[position]
        holder.binding.txtitle.text = data.Detail.toString()
        holder.binding.txtdescription.text = data.Detail.toString()
        holder.binding.txtAlarm.text = data.Time.toString()
        holder.itemView.setOnClickListener(View.OnClickListener {
            onclick.onClick(data)
        })
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class MyViewHolder(var binding: RecyclerLayoutBinding,var onclick: onItemClick): RecyclerView.ViewHolder(binding.root) {
        val title = binding.txtitle
        val description = binding.txtdescription
        val time = binding.txtAlarm
        //val deleteUserID = binding.deleteUserID

        fun bind(todo: Todo) {
            title.text = todo.Title
            description.text = todo.Detail
            time.text = todo.Time
            binding.deleteUserID.setOnClickListener {
                todoViewModel.deleteData(todo)

            }
        }
    }


}