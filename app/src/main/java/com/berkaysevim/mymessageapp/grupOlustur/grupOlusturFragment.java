package com.berkaysevim.mymessageapp.grupOlustur;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.berkaysevim.mymessageapp.GroupModel;
import com.berkaysevim.mymessageapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class grupOlusturFragment extends Fragment {

    EditText  grupAdi, grupdesc;
    ImageView grupResim;
    Button grupOlustur;
    RecyclerView grupRecycler;
    Uri imageUri;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;

    ArrayList<GroupModel> groupModelArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_grup_olustur, container, false);

        grupAdi = view.findViewById(R.id.nav_grupolustur_groupnamename);
        grupdesc= view.findViewById(R.id.nav_grupolustur_grupdescription);
        grupResim = view.findViewById(R.id.nav_grupolustur_groupImageImageView);
        grupOlustur = view.findViewById(R.id.nav_grupolustur);
        grupRecycler = view.findViewById(R.id.nav_grupolustur_groupsRecyclerView);

        groupModelArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        Intent data = result.getData();
                        imageUri = data.getData();
                        grupResim.setImageURI(imageUri);
                    }
                }
        );

        grupResim.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });

        grupOlustur.setOnClickListener(v -> {
            String groupName = grupAdi.getText().toString();
            String groupDescription = grupdesc.getText().toString();

            if (groupName.isEmpty()) {
                Toast.makeText(getContext(), "Grup ismi gerekli", Toast.LENGTH_SHORT).show();
                return;
            }
            if (groupDescription.isEmpty()) {
                Toast.makeText(getContext(), "Grup açıklaması gerekli", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imageUri != null) {

                StorageReference storageReference = firebaseStorage.getReference().child("resmiler/" + UUID.randomUUID().toString());

                storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        Toast.makeText(getContext(), "Resim yüklendi", Toast.LENGTH_SHORT).show();

                        CreateGroup(groupName, groupDescription, downloadUrl);
                    });
                });
                return;
            }else {
                CreateGroup(groupName, groupDescription, null);
            }
        });

        FetchGroups();
        return view;
    }

    private void CreateGroup(String name, String description, String image) {
        String userId = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("/userdata/" + userId + "/" + "groups").add(new HashMap<String, Object>(){{
            put("name", name);
            put("description", description);
            put("image", image);
            put("numbers", new ArrayList<String>());
        }}).addOnSuccessListener(documentReference -> {
            Toast.makeText(getContext(), "Grpu başarıyla oluşturuldu", Toast.LENGTH_SHORT).show();

            documentReference.get().addOnSuccessListener(documentSnapshot -> {
                GroupModel groupModel = new GroupModel( name, description, image, (List<String>)documentSnapshot.get("numbers"), documentSnapshot.getId());
                groupModelArrayList.add(groupModel);
                grupRecycler.getAdapter().notifyItemInserted(groupModelArrayList.size() - 1);
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Grup oluşturulamadı", Toast.LENGTH_SHORT).show();
        });
    }

    private void FetchGroups(){
        String userId = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("/userdata/" + userId + "/" + "groups").get().addOnSuccessListener(queryDocumentSnapshots -> {
            groupModelArrayList.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                GroupModel groupModel = new GroupModel(documentSnapshot.getString("name"), documentSnapshot.getString("description"), documentSnapshot.getString("image"), (List<String>)documentSnapshot.get("numbers"),documentSnapshot.getId());
                groupModelArrayList.add(groupModel);
            }

            grupRecycler.setAdapter(new GroupAdapter(groupModelArrayList));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            grupRecycler.setLayoutManager(linearLayoutManager);


        });
    }
}

