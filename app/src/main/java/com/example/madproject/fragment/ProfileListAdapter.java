package com.example.madproject.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madproject.R;
import com.example.madproject.classes.Post;
import com.example.madproject.classes.UploadUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    public Context context;
    public List<Post> postList;
    private ImageView imageView;

    public ProfileListAdapter(List<Post> postList, Context context) {
        this.postList=postList;
        this.context=context;
    }

    @NonNull
    @Override
    public ProfileListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("user")
                .whereEqualTo("id", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            final  String username =(document.getString("name"));
                            UploadUser user = document.toObject(UploadUser.class);
                            holder.textView.setText(user.getName());
                            String imageURL=postList.get(position).getImageURL();
                            Glide.with(context).load(imageURL).into(holder.imageView);
                            Glide.with(context).load(user.getImageUrl()).into(holder.imageViewProfile);
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        ImageView imageView,imageViewProfile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
        }
    }

}
