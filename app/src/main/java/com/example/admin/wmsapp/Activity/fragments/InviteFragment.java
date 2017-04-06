package com.example.admin.wmsapp.Activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wmsapp.Activity.Model.Contractor;
import com.example.admin.wmsapp.Activity.Model.InviteUser;
import com.example.admin.wmsapp.Activity.Model.Role;
import com.example.admin.wmsapp.Activity.Services.ContractorAPI;
import com.example.admin.wmsapp.Activity.Services.RoleAPI;
import com.example.admin.wmsapp.Activity.Services.pFactoryAPI;
import com.example.admin.wmsapp.Activity.activity.MainActivity;
import com.example.admin.wmsapp.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.admin.wmsapp.Activity.Model.Constants.ROOT_URL;

/**
 * Created by admin on 03/04/2017.
 */

public class InviteFragment extends Fragment implements  AdapterView.OnItemSelectedListener  {


    EditText editFirstName,editLastName,editEmailAddress,editPhone,editAddress,editContractorCode;
    SearchableSpinner spinner;

    Button buttonInviteUser,buttonCancel;

    ArrayList<Role> roleList=new ArrayList<>();
    Role role=new Role();
    String roleCode;

    String BASE_URL="http://192.168.100.40:1337/";

    Retrofit retrofit1=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    pFactoryAPI pApi=retrofit1.create(pFactoryAPI.class);
    InviteUser user=new InviteUser();

    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment_invite, container, false);
        editFirstName= (EditText) v.findViewById(R.id.editFirstName);
        editLastName= (EditText) v.findViewById(R.id.editLastName);
        editEmailAddress= (EditText) v.findViewById(R.id.editEmailAddress);
        editPhone= (EditText) v.findViewById(R.id.editPhone);
        editAddress= (EditText) v.findViewById(R.id.editAddress);
        editContractorCode= (EditText) v.findViewById(R.id.editContractorCode);

        editPhone.setVisibility(View.GONE);
        editAddress.setVisibility(View.GONE);
        editContractorCode.setVisibility(View.GONE);
        spinner= (SearchableSpinner) v.findViewById(R.id.spinner);

        buttonCancel= (Button) v.findViewById(R.id.buttonCancel);
        buttonInviteUser= (Button) v.findViewById(R.id.buttonInviteUser);

        getAllRole();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getActivity(), MainActivity.class));
             }
        });

        buttonInviteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              inviteUser();
            }
        });


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Invite Users");
    }

    private void getAllRole(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RoleAPI api=retrofit.create(RoleAPI.class);

        Call<ArrayList<Role>> call=api.getAllRole();

        call.enqueue(new Callback<ArrayList<Role>>() {
            @Override
            public void onResponse(Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Role>> call, Response<ArrayList<Role>> response) {
               if(response.body()!=null){
                roleList=response.body();
                showList();
               }
            }

            @Override
            public void onFailure(Call<ArrayList<com.example.admin.wmsapp.Activity.Model.Role>> call, Throwable t) {

            }
        });
    }

    private void showList(){
        ArrayAdapter<Role> dataAdapter=new ArrayAdapter<Role>(getActivity(),android.R.layout.simple_spinner_item,roleList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     role= (Role) parent.getSelectedItem();


        if(role.getRole_code().equals("CTR")){
            editPhone.setVisibility(View.VISIBLE);
            editAddress.setVisibility(View.VISIBLE);
            editContractorCode.setVisibility(View.VISIBLE);
       }
        if(role.getRole_code().equals("ADM")){
            editPhone.setVisibility(View.GONE);
            editAddress.setVisibility(View.GONE);
            editContractorCode.setVisibility(View.GONE);
        }

        if(role.getRole_code().equals("WHM")){
            editPhone.setVisibility(View.GONE);
            editAddress.setVisibility(View.GONE);
            editContractorCode.setVisibility(View.GONE);
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void inviteUser(){
        if(role.getRole_code().equals("CTR"))
        {
            Contractor contractor=new Contractor();
            contractor.setCtrName(editFirstName.getText().toString());
            contractor.setCtrEmailId(editEmailAddress.getText().toString());
            contractor.setCtrCode(editContractorCode.getText().toString());
            contractor.setCtrAddress(editAddress.getText().toString());
            contractor.setCtrStatus("Pending");
            contractor.setCtrNum(editPhone.getText().toString());
            contractor.setOrganisationId(MainActivity._orgId);
            ContractorAPI api=retrofit.create(ContractorAPI.class);

            Call<ResponseBody> call=api.createContractor(contractor);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        pUserInvite();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        else{
            user.setFname(editFirstName.getText().toString());
            user.setLname(editLastName.getText().toString());
            user.setEmail(editEmailAddress.getText().toString());
            user.setRole(role.getRole_code());
            user.setAppid(MainActivity._a);
            user.setOrgid(MainActivity._orgId);
            user.setOrgname(MainActivity._orgName);
            Call<ResponseBody> call=pApi.inviteUser(user);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getActivity(), "User Invited1 Successfully...!!!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }
         private void  pUserInvite(){
               user.setFname(editFirstName.getText().toString());
               user.setLname(editLastName.getText().toString());
               user.setEmail(editEmailAddress.getText().toString());
               user.setRole(role.getRole_code());
               user.setAppid(MainActivity._a);
               user.setOrgid(MainActivity._orgId);
               user.setOrgname(MainActivity._orgName);

             Call<ResponseBody> call=pApi.inviteUser(user);

             call.enqueue(new Callback<ResponseBody>() {
                 @Override
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     response.body();
                     Log.d("Res","res"+response.body());
                     Toast.makeText(getActivity(), "User Invited Successfully...!!!", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {

                 }
             });
     }

}
