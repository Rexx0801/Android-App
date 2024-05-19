//package com.example.th2.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.th2.R;
//
//public class FragmentInfo extends Fragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_info, container, false);
//    }
//}

package com.example.th2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.th2.LoginActivity;
import com.example.th2.R;

public class FragmentInfo extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        Button logoutButton = rootView.findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện đăng xuất
                logout();
            }
        });

        return rootView;
    }

    // Phương thức đăng xuất
    private void logout() {
        // Xóa bất kỳ thông tin phiên người dùng nào ở đây, chẳng hạn như thông tin đăng nhập
        // Điều chỉnh theo cách bạn lưu trữ thông tin phiên của người dùng

        // Trong phương thức logout():
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finishAffinity();

    }
}
