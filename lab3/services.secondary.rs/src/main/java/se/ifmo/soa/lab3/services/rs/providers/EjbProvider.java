package se.ifmo.soa.lab3.services.rs.providers;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.ext.Provider;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

@Provider
public class EjbProvider implements InjectionResolver<EJB> {

  @Override
  public Object resolve(final Injectee injectee, final ServiceHandle<?> root) {
    try {
      return InitialContext.doLookup(injectee.getRequiredType().getTypeName());
    } catch (final NamingException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean isConstructorParameterIndicator() {
    return false;
  }

  @Override
  public boolean isMethodParameterIndicator() {
    return false;
  }
}
