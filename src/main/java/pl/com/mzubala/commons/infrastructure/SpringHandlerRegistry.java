package pl.com.mzubala.commons.infrastructure;

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import pl.com.mzubala.commons.api.Handler;
import pl.com.mzubala.commons.domain.Command;

public class SpringHandlerRegistry implements HandlerRegistry,
    ApplicationListener<ContextRefreshedEvent> {

  private Map<Class<?>, String> handlers = new HashMap<>();

  private ConfigurableListableBeanFactory beanFactory;

  public SpringHandlerRegistry(ConfigurableListableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  @Override
  public Handler handlerFor(Command cmd) {
    String handlerBeanName = handlers.get(cmd.getClass());
    checkArgument(handlerBeanName != null, "no handler for command %s", cmd.getClass().getName());
    return beanFactory.getBean(handlerBeanName, Handler.class);
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    handlers.clear();
    Map<String, Handler> stringHandlerMap = beanFactory.getBeansOfType(Handler.class);
    for (String beanName : stringHandlerMap.keySet()) {
      Class<?> handlerClass = beanFactory.getType(beanName);
      handlers.put(getHandledCommandType(handlerClass), beanName);
    }
  }

  private Class<?> getHandledCommandType(Class<?> clazz) {
    Type[] genericInterfaces = clazz.getGenericInterfaces();
    ParameterizedType type = findByRawType(genericInterfaces, Handler.class);
    return (Class<?>) type.getActualTypeArguments()[0];
  }

  private ParameterizedType findByRawType(Type[] genericInterfaces, Class<?> expectedRawType) {
    for (Type type : genericInterfaces) {
      if (type instanceof ParameterizedType) {
        ParameterizedType parametrized = (ParameterizedType) type;
        if (expectedRawType.equals(parametrized.getRawType())) {
          return parametrized;
        }
      }
    }
    throw new RuntimeException();
  }

}
