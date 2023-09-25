package com.example.e_votehub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<UserModel> {

    private Context context;
    private List<UserModel> userList;

    public UserListAdapter(Context context, List<UserModel> userList) {
        super(context, 0, userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.activity_user_list_adapter, parent, false);
        }

        UserModel currentUser = userList.get(position);

        TextView txtName = listItemView.findViewById(R.id.txtName);
        TextView txtEmail = listItemView.findViewById(R.id.txtEmail);
        TextView txtUsername = listItemView.findViewById(R.id.txtUsername);

        txtName.setText("Name: " + currentUser.getName());
        txtEmail.setText("Email: " + currentUser.getEmail());
        txtUsername.setText("Username: " + currentUser.getUsername());

        return listItemView;
    }
}

