package ar.edu.frba.ddsi.servicio_agregador.models.entities.utils.solicitudEliminacion;

import org.springframework.stereotype.Component;

@Component
public class DetectorSpam implements DetectorDeSpam{

    //TODO la catedra  nunca nos proporciono un detector de spam, asi que por ahora no detecta nada
    @Override
    public boolean esSpam(String texto) {
        return false;
    }
}
