package com.prydev.ev2app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prydev.ev2app.R;
import com.prydev.ev2app.models.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<Car> carList;
    private MyItemClickListener myItemClickListener;

    public void setMyItemClickListener(MyItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public CarAdapter(List<Car> carList){
        this.carList = carList;
    }

    public interface MyItemClickListener{
        void OnClick(int pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView brand;
        TextView model;
        TextView place;
        TextView price;
        ImageView carImage;

        public ViewHolder(View view, MyItemClickListener click) {
            super(view);
            brand = view.findViewById(R.id.brand);
            model = view.findViewById(R.id.model);
            place = view.findViewById(R.id.place);
            price = view.findViewById(R.id.price);

            carImage = view.findViewById(R.id.carImage);

            view.setOnClickListener(view1 -> {
                int pos = getAdapterPosition();
                click.OnClick(pos);
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cars, parent, false);
        return new ViewHolder(view, this.myItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.brand.setText(car.getBrand());
        holder.model.setText(car.getModel());
        holder.place.setText(String.valueOf(position));
        holder.price.setText(String.valueOf(car.getPrice()));

        Glide.with(holder.itemView).load(car.getImageUrl()).into(holder.carImage);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public void deleteByPosition(int pos){
        carList.remove(pos);
        notifyItemRemoved(pos);
    }

}
