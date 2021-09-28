package mx.tecnm.tepic.ladm_u1_ejercicio2_trabajotodos

class Data {
    var nombre = ""
    var contenido = ""

    public fun contenido():String{
        return nombre+"&&C"+contenido;
    }

    public fun construir(renglon:String){
        val v=renglon.split("&&C")
        nombre = v[0]
        contenido = v[1]
    }
}