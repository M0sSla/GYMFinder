package com.example.gymfinder.ui.currentTraining;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymfinder.databinding.ExerciseTrainingItemBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExerciseTrainingAdapter extends RecyclerView.Adapter<ExerciseTrainingAdapter.ExerciseTrainingViewHolder> {

    private final ArrayList<ExerciseItemTraining> data;

    public ExerciseTrainingAdapter(ArrayList<ExerciseItemTraining> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ExerciseTrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseTrainingItemBinding binding = ExerciseTrainingItemBinding
                .inflate(LayoutInflater.from(
                        parent.getContext()), parent, false
                );
        return new ExerciseTrainingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseTrainingViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = (FirebaseAuth.getInstance()).getCurrentUser();
        DatabaseReference currentTraining = database.getReference("user").child(currentUser.getUid()).child("currentTraining");
        Query query = currentTraining.orderByChild("name").equalTo(data.get(position).getName());

        DatabaseReference currentVolume = database.getReference("user").child(currentUser.getUid()).child("currentVolume");

        holder.binding.nameExerciseTraining.setText(data.get(position).getName());
        holder.binding.groupExerciseTraining.setText(data.get(position).getGroup());
        if (data.get(position).getRepeats() != null) {
            holder.binding.repeats.setText((data.get(position).getRepeats()).toString());
        }
        else {
            holder.binding.repeats.setText("");
        }
        if (data.get(position).getWeight() != null) {
            holder.binding.weight.setText((data.get(position).getWeight()).toString());
        }
        else {
            holder.binding.weight.setText("");
        }

        holder.binding.repeats.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ExerciseItemTraining tmp = data.get(position);
                if (s.length() != 0) {
                    data.get(position).setRepeats(Integer.valueOf(s.toString()));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String key = childSnapshot.getKey();
                                DatabaseReference childRef = currentTraining.child(key);
                                childRef.child("repeats").setValue(Integer.valueOf(s.toString()));
                                View v = holder.binding.repeats;
                                /**/
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                ;
            }
        });

        holder.binding.weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ExerciseItemTraining tmp = data.get(position);
                if (s.length() != 0) {
                    data.get(position).setWeight(Integer.valueOf(s.toString()));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                                String key = childSnapshot.getKey();
                                DatabaseReference childRef = currentTraining.child(key);
                                childRef.child("weight").setValue(Integer.valueOf(s.toString()));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            DatabaseReference childRef = currentTraining.child(key);
                            childRef.removeValue();
                            data.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, data.size());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ExerciseTrainingViewHolder extends RecyclerView.ViewHolder {
        ExerciseTrainingItemBinding binding;
        public ExerciseTrainingViewHolder(ExerciseTrainingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
