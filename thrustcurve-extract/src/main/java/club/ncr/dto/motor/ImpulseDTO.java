package club.ncr.dto.motor;

public enum ImpulseDTO {

    //quarter_A("1/4A", 2.5/4),
    //half_A("1/2A", 2.5/2),
    A("A", 2.5),
    B("B", (int)(2.0 * A.maxNewtonSeconds)),
    C("C", (int)(2.0 * B.maxNewtonSeconds)),
    D("D", (int)(2.0 * C.maxNewtonSeconds)),
    E("E", (int)(2.0 * D.maxNewtonSeconds)),
    F("F", (int)(2.0 * E.maxNewtonSeconds)),
    G("G", (int)(2.0 * F.maxNewtonSeconds)),
    H("H", (int)(2.0 * G.maxNewtonSeconds)),
    I("I", (int)(2.0 * H.maxNewtonSeconds)),
    J("J", (int)(2.0 * I.maxNewtonSeconds)),
    K("K", (int)(2.0 * J.maxNewtonSeconds)),
    L("L", (int)(2.0 * K.maxNewtonSeconds)),
    M("M", (int)(2.0 * L.maxNewtonSeconds)),
    N("N", (int)(2.0 * M.maxNewtonSeconds)),
    O("O", (int)(2.0 * N.maxNewtonSeconds)),
    P("P", (int)(2.0 * O.maxNewtonSeconds)),
    Q("Q", (int)(2.0 * P.maxNewtonSeconds)),
    R("R", (int)(2.0 * Q.maxNewtonSeconds)),
    S("S", (int)(2.0 * R.maxNewtonSeconds)),
    T("T", (int)(2.0 * S.maxNewtonSeconds)),
    U("U", (int)(2.0 * T.maxNewtonSeconds)),
    V("V", (int)(2.0 * U.maxNewtonSeconds)),
    W("W", (int)(2.0 * V.maxNewtonSeconds)),
    X("X", (int)(2.0 * W.maxNewtonSeconds)),
    Y("Y", (int)(2.0 * X.maxNewtonSeconds)),
    Z("Z", (int)(2.0 * Y.maxNewtonSeconds))
    ;

    public final double minNewtonSeconds;
    public final double maxNewtonSeconds;
    public final String impulse;

    ImpulseDTO(String impulse, double maxNewtonSeconds) {
        this.impulse = impulse;
        this.maxNewtonSeconds= maxNewtonSeconds;
        this.minNewtonSeconds= maxNewtonSeconds / 2;
    }

    public static ImpulseDTO valueOf(Double thrust) {
        // if (thrust < quarter_A.maxNewtonSeconds) { return quarter_A; }
        if (thrust < A.maxNewtonSeconds) { return A; }
        for (ImpulseDTO impulse : values()) {
            if (thrust >= impulse.minNewtonSeconds && thrust <= impulse.maxNewtonSeconds) {
                return impulse;
            }
        }
        return Z;
    }

}
