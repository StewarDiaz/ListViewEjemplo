package com.programmingjd.listviewejemplo.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmingjd.listviewejemplo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarrosAdaptador extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Carro> carros;

    public CarrosAdaptador(Activity activity, ArrayList<Carro> cars) {
        this.activity = activity;
        this.carros = cars;
    }

    public void addCar(Carro car){
        carros.add(car);
    }

    public void addCar(ArrayList<Carro> carsElements){
        carros.addAll(carsElements);
    }

    @Override
    public int getCount() {
        return carros.size();
    }

    @Override
    public Object getItem(int position) {
        return carros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.carro_item, null);
        }
        Carro carElement = carros.get(position);

        TextView nameCar = v.findViewById(R.id.tvCarName);
        nameCar.setText(carElement.getName());

        TextView carValue = v.findViewById(R.id.tvCarValue);
        carValue.setText(carElement.getValue());

        TextView carModel = v.findViewById(R.id.tvCarModel);
        carModel.setText(carElement.getModel());

        TextView carCylinderCapacity = v.findViewById(R.id.tvCarCC);
        carCylinderCapacity.setText(carElement.getCylinderCapacity());

        ImageView photo = v.findViewById(R.id.ivCarImg);
        Picasso.with(activity).load(carElement.getImage()).into(photo);
        return v;
    }
}
