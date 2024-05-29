package br.com.notesrelease.Util;

import br.com.notesrelease.componentes.DefaultUser;
import br.com.notesrelease.infra.exceptions.NegocioException;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.notesrelease.infra.exceptions.Errors.ERRO_GENERICO;

/**
 * Created by DEINF.MHVAZ on 19/03/2019.
 */
@Component
public class Util {

    // crtl + alt + v = cria variavel para função que retorna
    // crtl + alt + c = cria constate para função que retorna
    public static final String DEV = "DEV";
    public static final String LOCAL = "LOCAL";
    public static final String PROD = "PROD";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USUARIO = "USUARIO";
    public static final String ROLE_PRODUTOR = "PRODUTOR";
    public static final String ROLE_GESTOR = "GESTOR";


    public static final String PATTERN_TUDO_A_FRENTE = "/**";
    public static final String BARRA = "/";
    public static final String BARRAS = "//";
    public static final String API = "api";

    public static final String ANGULAR_ROUTE = "#!";


    public static final String COMUM = "comum";
    public static final String RESTRITO = "restrito";
    public static final String ADMIN = "admin";
    public static final String GESTAO = "gestao";
    public static final String USUARIO = "usuario";
    public static final String PRODUTOR = "produtor";
    public static final String DENUNCIA = "denuncia";
    public static final int ULTIMA_HORA_E_MINUTO = 86340000;
    public static final int TAMANHO_DESC_EVENTO = 2000;

    public static final String COMFIRMA_CADASTRO = "confirmar-cadastro";
    public static final String SOLICITA_EMAIL_TROCA_SENHA = "validar-email-troca-senha";
    public static final String PERFIL = "perfil";


    public static final String TROCA_SENHA = "trocar-senha";

    public static final String API_ROUTE = Util.BARRA + Util.API + Util.BARRA;

    public static final String ROTA_RESTRITO = BARRA + API + BARRA + RESTRITO;


    public static final Logger LOGGER = LoggerFactory.getLogger(Util.class);


    public static boolean naoNulo(Object object) {
        return object != null;
    }

    public static boolean nulo(Object object) {
        return object == null;
    }

    public static boolean idValido(Object object) {
        if (Util.nulo(object)) {
            return false;
        } else {
            Long value = Long.parseLong(object.toString());
            return !Long.valueOf(0).equals(value);
        }

    }

    public static <T extends Collection> boolean naoNuloeVazio(T object) {
        return (Util.naoNulo(object) && !object.isEmpty());
    }

    public static <T extends Collection> boolean nuloOuVazio(T object) {
        return (Util.nulo(object) || object.isEmpty());
    }

    public static String recuperaUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome;
        if (principal instanceof UserDetails) {
            nome  = ((UserDetails)principal).getUsername();
        } else {
            nome = principal.toString();
        }
        return nome;
    }

    public static boolean emBranco(String valor) {
        return nulo(valor) || valor.trim().isEmpty();
    }

    public static boolean nulosOuEmBranco(Object... valores) {
        boolean retorno = false;
        for (Object valor: valores) {
            if (valor instanceof String) {
                retorno = retorno || emBranco((String) valor);
            } else {
                retorno = retorno || nulo(valor);
            }
        }
        return retorno;
    }

    public static Object converter(Object objeto, Class tipo) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(objeto , tipo);
    }

    public static Object converter(Object objeto, Type tipo) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(objeto , tipo);
    }

    private static <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class && clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static <T extends Class> List<Field> getSubFields(Field t) {
        List<Field> fields = new ArrayList<>();
        Class clazz;
        if (t.getType().getSuperclass() == null) {
            try {
                ParameterizedType genericType = (ParameterizedType) t.getGenericType();
                clazz = (Class<?>) genericType.getActualTypeArguments()[0];
            } catch (ClassCastException e) {
                clazz = null;
            }
        } else {
            clazz = t.getType();
        }

        while (clazz != Object.class && clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    public  static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        String fileName = multipart.getOriginalFilename();
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    public static File alteraNomeArquivo(MultipartFile arquivo, String novoNome) {
        File arquivoFinal = null;
        try {
            arquivoFinal = multipartToFile(arquivo);
            arquivoFinal.renameTo(new File(arquivoFinal.getParentFile().getPath() + BARRAS + novoNome));
        } catch (IOException e) {
            LOGGER.error("Nao foi possivel alterar o nome do arquivo " + arquivo.getOriginalFilename());
        }
        return arquivoFinal;
    }

    public static byte[] retornaBytes(File file){
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }

    public static File retornaFile(byte[] bytes, String fileName) throws IOException {
        Path path = new File(System.getProperty("java.io.tmpdir")+"/"+fileName).toPath();
        //Files.write(path, bytes);
        File retorno = path.toFile();
        //FileUtils.writeByteArrayToFile(new File(System.getProperty("java.io.tmpdir")+"/"+fileName), bytes);
        FileUtils.writeByteArrayToFile(new File(System.getProperty("java.io.tmpdir")+"/"+fileName), bytes);
        return retorno;
    }

    public static String extractMimeType(final String encoded) {
        final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*");
        final Matcher matcher = mime.matcher(encoded);
        if (!matcher.find())
            return "";
        return matcher.group(1).toLowerCase();
    }

    public static String retoraExtensaoBase64(String base64) {
        String mime = extractMimeType(base64);
        String[] valores = mime.split("/");
        return valores[1];
    }

    public static double getFileSizeMegaBytes(File file) {
        return file.length() / (1024 * 1024);
    }

    public static double getFileSizeKiloBytes(File file) {
        return file.length() / 1024;
    }

    public static double getFileSizeBytes(File file) {
        return file.length();
    }

    public static String tiraAcento (String passa){
        if (passa == null) {
            return null;
        }
        passa = passa.replaceAll("[ÂÀÁÄÃ]","A");
        passa = passa.replaceAll("[âãàáä]","a");
        passa = passa.replaceAll("[ÊÈÉË]","E");
        passa = passa.replaceAll("[êèéë]","e");
        passa = passa.replaceAll("[ÎÌÏÍ]","I");
        passa = passa.replaceAll("[îíìï]","i");
        passa = passa.replaceAll("[ÔÕÒÓÖ]","O");
        passa = passa.replaceAll("[ôõòóö]","o");
        passa = passa.replaceAll("[ÛÙÚÜ]","U");
        passa = passa.replaceAll("[ûúùü]","u");
        passa = passa.replaceAll("Ç","C");
        passa = passa.replaceAll("ç","c");
        passa = passa.replaceAll("[ýÿ]","y");
        passa = passa.replaceAll("Ý","Y");
        passa = passa.replaceAll("ñ","n");
        passa = passa.replaceAll("Ñ","N");
        passa = passa.replaceAll("º","");
        passa = passa.replaceAll("ª","");
        return passa;
    }

    public static ModelMapper mapper() {
        return new ModelMapper();
    }
    /*
    public static boolean isMesmoUsuarioLogado(Usuario usuario) {
        return
    }

     */

    public static DefaultUser usuarioLogado() throws NegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return  (DefaultUser) authentication.getDetails();

        } else {
            throw new NegocioException("Usuário não autenticado");
        }
    }

    public boolean isEmail(String texto) {
        //Regular Expression
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        //Create instance of matcher
        Matcher matcher = pattern.matcher(texto);

        return matcher.matches();

    }

    public static NegocioException erroGenerico() throws NegocioException {
        throw new NegocioException(ERRO_GENERICO);
    }

    public static double sizeFromBase64(String data) {
        if (data == null) {
            return 0;
        }
        return Math.ceil(data.length() / 4) * 3;
    }
}
