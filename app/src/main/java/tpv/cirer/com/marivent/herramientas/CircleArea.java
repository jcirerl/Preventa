package tpv.cirer.com.marivent.herramientas;

/**
 * Created by JUAN on 13/08/2017.
 */

public class CircleArea {
    /** Stores data about single circle */
        public float radius;
        public float centerX;
        public float centerY;
        public String circulo;
        public String mesacirculo;

        public CircleArea(float centerX, float centerY, float radius, String circulo, String mesacirculo) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
            this.circulo = circulo;
            this.mesacirculo = mesacirculo;
        }
    public String getCirculo() {
        return circulo;
    }
    public void setCirculo(String idcirculo) {
        this.circulo = idcirculo;
    }
    public String getMesacirculo() {
        return mesacirculo;
    }
    public void setMesacirculo(String idmesacirculo) {
        this.mesacirculo = idmesacirculo;
    }

        @Override
        public String toString() {
            return "Circle[" + centerX + ", " + centerY + ", " + radius +  ", " + circulo + ", " + mesacirculo +"]";
        }

}
