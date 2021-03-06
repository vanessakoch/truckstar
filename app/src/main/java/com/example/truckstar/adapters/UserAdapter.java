package com.example.truckstar.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.truckstar.R;
import com.example.truckstar.activities.EditProviderActivity;
import com.example.truckstar.activities.EditUserActivity;
import com.example.truckstar.activities.HomeActivity;
import com.example.truckstar.activities.MainActivity;
import com.example.truckstar.database.AppDatabase;
import com.example.truckstar.entities.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter {

    List<User> usersList;
    private int recently_removed_position;
    private User recently_removed_user;
    AppCompatActivity activity;
    AppDatabase db;

    public UserAdapter(AppCompatActivity activity) {
        this.activity = activity;
        this.usersList = new ArrayList<User>();
        db = AppDatabase.getDatabase(activity);

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<User> list = db.userDao().getAll();
                for(User p : list)
                    usersList.add(p);
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        UserViewHolder viewHolder = new UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        UserViewHolder viewHolder = (UserViewHolder) holder;
        viewHolder.txtNameUser.setText(usersList.get(position).getName());
        viewHolder.txtNickUser.setText("Login: " + usersList.get(position).getNickname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usersList.get(holder.getAdapterPosition()).getName().equalsIgnoreCase("Admin")) {
                    Intent intent = new Intent(activity.getBaseContext(), EditUserActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", usersList.get(holder.getAdapterPosition()));
                    bundle.putInt("position", holder.getAdapterPosition());
                    bundle.putInt("request_code", MainActivity.REQUEST_EDIT_USER);
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, MainActivity.REQUEST_EDIT_USER);
                } else {
                    makeToast("Usuário admin não pode ser editado!", R.drawable.ic_error_outline);
                }
            }
        });

        viewHolder.btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void remove(final int position) {
        recently_removed_position = position;
        recently_removed_user = usersList.get(position);

        if(!recently_removed_user.getName().equalsIgnoreCase("Admin")) {
            AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.userDao().deleteUser(recently_removed_user);
                }
            });

            usersList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, this.getItemCount());

            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = LayoutInflater.from(activity);
            View v = inflater.inflate(R.layout.dialog_remove, null);

            Button btnConfirm = (Button) v.findViewById(R.id.btnConfirm);
            Button btnCancel = (Button) v.findViewById(R.id.btnCancel);

            mBuilder.setView(v);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeToast("Item removido com sucesso!", R.drawable.ic_emoticon_smile);
                    dialog.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppDatabase.databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            db.userDao().insertUser(recently_removed_user);
                        }
                    });

                    usersList.add(recently_removed_position, recently_removed_user);
                    notifyItemInserted(recently_removed_position);
                    dialog.dismiss();
                }
            });
        } else {
            makeToast("Usuário admin não pode ser apagado!", R.drawable.ic_error_outline);
        }
    }

    public void insert(final User user){

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().insertUser(user);
            }
        });
        usersList.add(user);
        notifyItemInserted(getItemCount());
    }

    public void move(int fromPosition, int toPosition){
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(usersList, i, i+1);
        else
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(usersList, i, i-1);
        notifyItemMoved(fromPosition,toPosition);
    }

    public void makeToast(String text, int image) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) activity.findViewById(R.id.toast_root));
        TextView textToast = layout.findViewById(R.id.toast_text);
        ImageView toastImage = layout.findViewById(R.id.toast_image);
        textToast.setText(text);
        toastImage.setImageResource(image);
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void edit(final User user, final int position) {
        usersList.get(position).setName(user.getName());
        usersList.get(position).setNickname(user.getNickname());
        usersList.get(position).setPassword(user.getPassword());

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().updateUser(user);
            }
        });
        notifyItemChanged(position);
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.userDao().deleteAll();
            }
        });
        while (!usersList.isEmpty()){
            usersList.remove(0);
            notifyItemRemoved(0);
            notifyItemRangeChanged(0,this.getItemCount());
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameUser;
        TextView txtNickUser;
        ImageView btnRemoveUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            txtNameUser = (TextView) itemView.findViewById(R.id.txtNameUser);
            txtNickUser = (TextView) itemView.findViewById(R.id.txtNickUser);
            btnRemoveUser = (ImageView) itemView.findViewById(R.id.btnRemoveUser);
        }
    }
}