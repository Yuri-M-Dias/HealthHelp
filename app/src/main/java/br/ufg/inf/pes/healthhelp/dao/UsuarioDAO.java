package br.ufg.inf.pes.healthhelp.dao;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufg.inf.pes.healthhelp.model.Usuario;
import br.ufg.inf.pes.healthhelp.model.event.DatabaseEvent;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class.getCanonicalName(), "usuario", Usuario.class);
    }

    @Override
    public void buscarTodos() {
        getDatabaseReference()
            .child(DATABASE_CHILD)
            .addListenerForSingleValueEvent(
                new ValueEventListener() {
                    List<Usuario> usuarios = new ArrayList<Usuario>();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w(TAG, "buscarTodos.addValueListener: OnDataChange: Quantidade: " +
                            dataSnapshot.getChildrenCount());

                        for (DataSnapshot usuarioSnapshot : dataSnapshot.getChildren()) {
                            Usuario usuarioRecebido = usuarioSnapshot.getValue(Usuario.class);
                            usuarioRecebido.setId(usuarioSnapshot.getKey());
                            usuarios.add(usuarioRecebido);
                            Log.w(TAG, "Obtido: " + usuarios.get(usuarios.size() - 1).getId());
                        }
                        EventBus.getDefault().post(new DatabaseEvent<>(usuarios));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "buscarTodos.setValueListener: onCancelled:", databaseError.
                            toException());
//                getDatabaseCallback().onError(databaseError);
                    }
                });
    }

    @Override
    public void buscarPelaId(String id) {
        getDatabaseReference().child(DATABASE_CHILD).orderByChild("id").equalTo(id).
            addListenerForSingleValueEvent(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(DataSnapshot dataSnapshot) {
                                                   GenericTypeIndicator<HashMap<String, Usuario>> genericTypeIndicator =
                                                       new GenericTypeIndicator<HashMap<String, Usuario>>() {
                                                       };
                                                   HashMap<String, Usuario> usuarioHashMap = dataSnapshot.
                                                       getValue(genericTypeIndicator);
                                                   Usuario usuarioEncontrado = usuarioHashMap.values().iterator().next();
                                                   Log.w(TAG, "buscaPeloId.OnDataChange: o usuário encontrado foi: " +
                                                       usuarioEncontrado.getNome());

                                                   // chama de volta quem pediu o local que foi buscado
//                        getDatabaseCallback().onComplete(usuarioEncontrado);
                                               }

                                               @Override
                                               public void onCancelled(DatabaseError databaseError) {
                                                   Log.e(TAG, "buscaPelaId: onCancelled:", databaseError.toException());
//                        getDatabaseCallback().onError(databaseError);
                                               }
                                           }
            );
    }

    @Override
    public void inserir(Usuario usuario) {
        DatabaseReference registroUsuario = getDatabaseReference().child(DATABASE_CHILD).push();
        Log.i(TAG, "Chave do novo usuario: " + registroUsuario.getKey());
        registroUsuario.setValue(usuario);
        usuario.setId(registroUsuario.getKey());
    }

    public void buscarPorLogin(final String login, final String senha) {
        //TODO: Substituir a implementação abaixo pela implementação utilizando Firebase
        final Usuario usuario = new Usuario();
        usuario.setId("123");
        usuario.setNome("Cleber Alcântara");
        usuario.setLogin("cleber");
        usuario.setSenha("cleber");

        AsyncExecutor.create().execute(
            new AsyncExecutor.RunnableEx() {
                @Override
                public void run() throws Exception {
                    if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                        EventBus.getDefault().post(new DatabaseEvent<>(usuario));
                    } else {
                        throw new Exception("Usuário e/ou senha inválidos");
                    }
                }
            }
        );
    }

}
