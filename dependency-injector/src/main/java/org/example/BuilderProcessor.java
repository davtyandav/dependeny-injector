package org.example;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import com.google.auto.service.AutoService;

@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(value = Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Component.class.getName());
    }

    static boolean foo = false;

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if(foo){
            return false;
        }
        foo = true;
        processingEnv.getMessager().printMessage(
            Kind.WARNING,
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        StringBuilder builder = new StringBuilder();

        for (Element e : roundEnv.getElementsAnnotatedWith(Component.class)) {
            TypeElement clazz = (TypeElement) e;

            processingEnv.getMessager().printMessage(
                Kind.WARNING,
                "className: " + clazz);

            builder.append(
                    "beans.put(%s.class, new %s());".formatted(clazz.getQualifiedName(), clazz.getQualifiedName()))
                .append("\n");
        }



        try {
            JavaFileObject f = processingEnv.getFiler().
                createSourceFile("org.example.ApplicationContextImpl");
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                                                     "Creating " + f.toUri());

            Writer w = f.openWriter();

            PrintWriter pw = new PrintWriter(w);
            pw.println("""
                           package org.example;

                           import java.util.HashMap;
                           import java.util.Map;

                           public class ApplicationContextImpl implements ApplicationContext {

                               private final Map<Class<?>, Object> beans = new HashMap<>();

                               public ApplicationContextImpl() {
                                  %s
                               }

                               @Override
                               public <T> T getBean(Class<T> clazz) {
                                   return (T) beans.get(clazz);
                               }
                           }

                           """.formatted(builder.toString()));
            pw.flush();

            w.close();
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                                     e.toString());
        }

        return false;
    }

}