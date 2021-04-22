package Model;

public enum TipoCliente {
    PISTA, CAMAROTE, VIP;

    private static final float valorEntrada = 10;

    public float getValorEntrada(){
        switch (this) {
            case VIP: return valorEntrada * 2;
            case CAMAROTE: return (float) (valorEntrada * 1.5);
            case PISTA: default: return valorEntrada;
        }
    }
}
