package edo.firebaserealtimedatabase;

import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText etnama,etalamat,etnomortelpon,etkelas;
    private TextView tvnama,tvalamat,tvnomortelpon,tvkelas;
    private Button push;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindingData();
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ini bagian Insert
                myRef = database.getReference("Profile"); //Membuat Judul node pada database yang baru
                id = myRef.push().getKey();
                myRef.child(id).child("Nama").setValue(getNama());
                myRef.child(id).child("Alamat").setValue(getAlamat());
                myRef.child(id).child("Nomortelpon").setValue(getNomortelpon());
                myRef.child(id).child("Kelas").setValue(getKelas());

                //Ini bagian Read data
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String nama = dataSnapshot.child(id).child("Nama").getValue(String.class);
                        String alamat = dataSnapshot.child(id).child("Alamat").getValue(String.class);
                        String nomortelpon = dataSnapshot.child(id).child("Nomortelpon").getValue(String.class);
                        String kelas = dataSnapshot.child(id).child("Kelas").getValue(String.class);

                        //Set TextView
                        tvnama.setText(nama);
                        tvalamat.setText(alamat);
                        tvnomortelpon.setText(nomortelpon);
                        tvkelas.setText(kelas);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Error : "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                bersihkanEdittext();
            }
        });

    }

    private void bindingData(){
        etnama = (EditText)findViewById(R.id.nama);
        etalamat = (EditText)findViewById(R.id.alamat);
        etnomortelpon = (EditText)findViewById(R.id.nomortelpon);
        etkelas = (EditText)findViewById(R.id.kelas);
        tvnama = (TextView)findViewById(R.id.tvnama);
        tvalamat = (TextView)findViewById(R.id.tvalamat);
        tvnomortelpon = (TextView)findViewById(R.id.tvnomortelpon);
        tvkelas = (TextView)findViewById(R.id.tvkelas);
        push = (Button)findViewById(R.id.btn);
    }

    private void bersihkanEdittext(){

        etnama.setText("");
        etalamat.setText("");
        etkelas.setText("");
        etnomortelpon.setText("");

    }

    private String getNama(){
        return etnama.getText().toString().trim();
    }
    private String getAlamat(){
        return etalamat.getText().toString().trim();
    }
    private String getNomortelpon(){
        return etnomortelpon.getText().toString().trim();
    }
    private String getKelas(){
        return etkelas.getText().toString().trim();
    }

}
