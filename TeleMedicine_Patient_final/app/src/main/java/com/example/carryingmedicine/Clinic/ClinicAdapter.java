package com.example.carryingmedicine.Clinic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carryingmedicine.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ViewHolder> implements OnClinicItemClickListener, Filterable {
    List<Clinic> itemsAll;
    List<Clinic> items;   // 검색 시 해당 검색만 가지고있을 리스트 객체
    OnClinicItemClickListener listener;

    public ClinicAdapter(List<Clinic> example) {  //생성자로 들어온 example 로 각 리스트를 초기화
        this.items = example;
        this.itemsAll= new ArrayList<>(example);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.clinic_layout,parent,false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Clinic item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(Clinic item)
    {
        items.add(item);
    }
    public Clinic getItem(int position) { return items.get(position); }



    public void setItems(ArrayList<Clinic> items)
    {
        this.items=items;
    }
    public Clinic setItem(int position, Clinic item)
    {
        return items.set(position,item);
    }

    public void setOnItemClickListener(OnClinicItemClickListener listener) {
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
            List<Clinic> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0) { //검색된 정보가 없을경우
                filteredList.addAll(itemsAll);                   //모든정보를 저장
            }
            else
            {
                String s  = constraint.toString().toLowerCase().trim();

                for(Clinic item : itemsAll)
                {
                    if(item.getDoctorName().toLowerCase().contains(s)||item.getReservation_date().toLowerCase().contains(s)||item.getReservation_time().toLowerCase().contains(s))
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
            items.addAll((Collection<? extends Clinic>)results.values);
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

//////////뷰홀더
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView re_number,re_doctorName, re_date, re_time;

        public ViewHolder(@NonNull View itemView,final OnClinicItemClickListener listener) {
            super(itemView);
            re_number = itemView.findViewById(R.id.re_number);
            re_doctorName = itemView.findViewById(R.id.re_doctorName);
            re_date = itemView.findViewById(R.id.re_date);
            re_time =itemView.findViewById(R.id.re_time);
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
    public void setItem(Clinic item)
    {
        re_number.setText("예약번호\n\n"+item.getReservation_number());
        re_doctorName.setText("의사이름 : "+item.getDoctorName());
        re_date.setText("예약날짜 : "+item.getReservation_date());
        re_time.setText("예약시간 : "+item.getReservation_time());
    }
//reservation_date,userName,reservatioon_numbeer,u
    }

}
