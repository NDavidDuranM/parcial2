#PROBADO EN PYTHON 3
from suds.client import Client #pip install suds-py3

def main():
    url = "http://localhost:7000/ws/AcademicoWebService?wsdl"
    client = Client(url)

    #listar estudiantes
    lst_estudiantes = client.service.getListaEstudiante()
    print('1) Lista de todos los estudiantes: ')
    for estudiante in lst_estudiantes:
        print(estudiante)

    #findById()
    id = int(input('2) busqueda de un estudiante, digite matricula del estudiante: '))
    estudiante = client.service.getEstudiante(id)
    if (estudiante != None):
        print(estudiante)
    else:
        print('no se encontro ningun estudiante')

    #post
    id = int(input('3) Ahora vamos a crer un estudiante, primero digite la matricula: '))
    nombre = input('ahora el nombre: ')
    carrera = input('por ultimo su carrera: ')

    estudiante = client.factory.create('estudiante')
    estudiante.matricula = id
    estudiante.nombre = nombre
    estudiante.carrera = carrera
    client.service.crearEstudiante(estudiante)
    print('Se creo el estudiante')

    #borrar
    id = int(input('4) Por ultimo vamos a eliminar un estudiante, ingrese la matricula del estudiante a borrar: '))
    client.service.borrarEstudiante(id)
    print(f'Se ha eliminado el estudiante de matricula {id}')
    """ HACE FALTA AGREGAR EL SIGUIENTE METODO EN EL SERVIDOR DEL PROYECTO javalin-demo
    @WebMethod
    public Boolean borrarEstudiante(int matricula){
        return fakeServices.eliminandoEstudiante(matricula);
    } """


if __name__ == "__main__":
    main()