import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int matricula;
        String carrera;
        String nombre;
        int num =1;
        do{
            System.out.println("Menu:\n1.Agregar estudiante\n2.Buscar Estudiante\n3.Eliminar Estudiante\n4.Listar estudiantes\nSeleccione:");
            num = sc.nextInt();
            switch(num)
            {
                case 1 :
                    System.out.println("Ingrese la matricula: ");
                    matricula = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Ingrese la carrera: ");
                    carrera = sc.nextLine();
                    System.out.println("Ingrese el nombre: ");
                    nombre = sc.nextLine();
                    guardarEstudiante(matricula,nombre,carrera);

                case 2 :
                    System.out.println("Ingrese la matricula: ");
                    matricula = sc.nextInt();
                    buscarEstudiante(matricula);

                case 3 :
                    System.out.println("Ingrese la matricula: ");
                    matricula = sc.nextInt();
                    eliminarEstudiante(matricula);

                case 4 :
                    buscarListaEstudiante();

                default:
                    System.out.println("Opcion incorrecta");
            }
        }while(num != 0);
        String cadena = sc.nextLine();


    }
    public static void guardarEstudiante(int mat, String nam, String car)
    {

        HttpResponse<JsonNode> estudiantePOST = Unirest.post("http://localhost:7000/api/estudiante/")
                .header("accept", "application/json")
                .queryString("apiKey", "123")
                .body(new Estudiante(mat,nam,car))
                .asJson();
        System.out.println("El codigo de respuesta es: "+estudiantePOST.getStatus());
        System.out.println("El mensaje es: "+estudiantePOST.getBody().toString());
    }
    public static void buscarEstudiante(int mat)
    {
        Estudiante estudianteGET = Unirest.get("http://localhost:7000/api/estudiante/"+mat)
                .asObject(Estudiante.class)
                .getBody();

        System.out.println("matricula="+estudianteGET.getMatricula()+", Nombre="+estudianteGET.getNombre()+", carrera="+estudianteGET.getCarrera());
    }
    public static void eliminarEstudiante(int mat)
    {
        HttpResponse estudianteDELETE = Unirest.delete("http://localhost:7000/api/estudiante/"+mat).asEmpty();
        System.out.println("El codigo de respuesta es: "+estudianteDELETE.getStatus());
    }

    public static void buscarListaEstudiante()
    {
        HttpResponse<List<Estudiante>> estudianteLISTA = Unirest.get("http://localhost:7000/api/estudiante/")
                .header("accept", "application/json")
                .queryString("apiKey", "123")
                .asObject(new GenericType<List<Estudiante>>() {});
        System.out.println("El codigo de respuesta es: "+estudianteLISTA.getStatus());
        System.out.println("El mensaje es: "+estudianteLISTA.getBody().toString());
    }

}