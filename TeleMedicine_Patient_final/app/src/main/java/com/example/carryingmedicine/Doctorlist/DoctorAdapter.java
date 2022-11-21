package com.example.carryingmedicine.Doctorlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carryingmedicine.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> implements OnDoctorItemClickListener, Filterable {
    List<Doctor> itemsAll; //검색시 아무것도 검색을 안했을때 모든 정보를 담고있는 리스트 객체
    List<Doctor> items;   // 검색 시 해당 검색만 가지고있을 리스트 객체
    OnDoctorItemClickListener listener; // 클릭이벤트에 대한 listener

    public DoctorAdapter(List<Doctor> example) {  //생성자로 들어온 example 로 각 리스트를 초기화
        this.items = example;
        this.itemsAll= new ArrayList<>(example);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.doctor_layout,parent,false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(Doctor item)
    {
        items.add(item);
    }
    public Doctor getItem(int position) { return items.get(position); }



    public void setItems(ArrayList<Doctor> items)
    {
        this.items=items;
    }
    public Doctor setItem(int position, Doctor item)
    {
        return items.set(position,item);
    }

    public void setOnItemClickListener(OnDoctorItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    private Filter exampleFilter = new Filter() {  //검색을 수행할 필터 함수
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Doctor> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0) { //검색된 정보가 없을경우
                filteredList.addAll(itemsAll);                   //모든정보를 저장
            }
            else
            {
                String s  = constraint.toString().toLowerCase().trim();

                for(Doctor item : itemsAll)
                {
                    if(item.getName().toLowerCase().contains(s)||item.getMajor().toLowerCase().contains(s)||item.getSchool().toLowerCase().contains(s))
                    { //검색된 값과 일치하면 그 값을 필터리스트에 저장후
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results; //값을 반환
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            items.clear();
            items.addAll((Collection<? extends Doctor>)results.values);
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    //////////뷰홀더 클래스//////////////////////////////////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView d_t,school_text,docotr_class;
        EditText doctor_se;
        public ViewHolder(@NonNull View itemView,final OnDoctorItemClickListener listener) {
            super(itemView);
            d_t = itemView.findViewById(R.id.d_t);
            school_text = itemView.findViewById(R.id.school_text);
            docotr_class=itemView.findViewById(R.id.doctor_class);


            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ViewHolder.this, view, position);
                }
            }

        });


    }
    public void setItem(Doctor item)
    {
        d_t.setText(item.getName());
        school_text.setText(item.getHospital_name());
        docotr_class.setText(item.getMajor());
    }
    }


}
