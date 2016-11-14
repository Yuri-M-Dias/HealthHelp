package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Usuario;

/**
 * Created by gleibson on 10/11/16.
 */

public class UsuarioDAO extends AbstractDAO<Usuario> {

    public UsuarioDAO(String TAG, String DATABASE_CHILD) {
        super(TAG, DATABASE_CHILD);
    }

    /**
     * Busca todas as instancias de usuários guardados no banco
     */
    @Override
    public void buscarTodos() {
        getDatabaseReference().child(DATABASE_CHILD).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<Usuario> usuarios = new ArrayList<Usuario>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.w(TAG, "buscarTodos.addValueListener: OnDataChange: Quantidade: " +
                dataSnapshot.getChildrenCount());

                for (DataSnapshot usuarioSnapshot: dataSnapshot.getChildren()) {
                    Usuario usuarioRecebido = usuarioSnapshot.getValue(Usuario.class);
                    usuarioRecebido.setId(usuarioSnapshot.getKey());
                    usuarios.add(usuarioRecebido);
                    Log.w(TAG,"Obtido: " + usuarios.get(usuarios.size()-1).getId());
                }
                getDatabaseCallback().onComplete(usuarios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "buscarTodos.setValueListener: onCancelled:", databaseError.
                        toException());
                getDatabaseCallback().onError(databaseError);
            }
        });
    }

    /**
     *
     * @param id parâmetro usado para encontrar o usuário desejado no banco
     */
    @Override
    public void buscarPelaId(String id) {
        getDatabaseReference().child(DATABASE_CHILD).orderByChild("id").equalTo(id).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<HashMap<String, Usuario>> genericTypeIndicator =
                                new GenericTypeIndicator<HashMap<String, Usuario>>() {};
                        HashMap<String, Usuario> usuarioHashMap = dataSnapshot.
                                getValue(genericTypeIndicator);
                        Usuario usuarioEncontrado = usuarioHashMap.values().iterator().next();
                        Log.w(TAG, "buscaPeloId.OnDataChange: o usuário encontrado foi: " +
                                usuarioEncontrado.getNome());

                        // chama de volta quem pediu o local que foi buscado
                        getDatabaseCallback().onComplete(usuarioEncontrado);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "buscaPelaId: onCancelled:", databaseError.toException());
                        getDatabaseCallback().onError(databaseError);
                    }
                }
        );
    }

    /**
     *
     * @param usuario instância do objeto usuário a qual será inserida no banco
     */
    @Override
    public void inserir(Usuario usuario) {
        DatabaseReference registroUsuario = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo usuario: " + registroUsuario.getKey());
        registroUsuario.setValue(usuario);
        usuario.setId(registroUsuario.getKey());
    }

    /**
     *
     * @param usuario instância do objeto usuário a qual será removida do banco
     */
    @Override
    public void remover(Usuario usuario) {
        getDatabaseReference().child(DATABASE_CHILD).child(String.valueOf(usuario.getId())).
                removeValue();
    }

    /**
     *
     * @param usuario instância do objeto usuário a qual será atualizada no banco
     */
    @Override
    public void atualizar(Usuario usuario) {
        getDatabaseReference().child(DATABASE_CHILD).child(usuario.getId()).setValue(usuario);
    }
}
