package se.ifmo.soa.lab1.utils;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import javax.validation.ConstraintViolation;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;

@NoArgsConstructor(access = PRIVATE)
public class ConstraintViolationMessageBuilder {

  public static String buildErrorMessage(
      final Collection<? extends ConstraintViolation<?>> violations) {
    final StringBuilder messageBuilder = new StringBuilder();

    for (final ConstraintViolation<?> violation : violations) {
      final PathImpl propertyPath = (PathImpl) violation.getPropertyPath();
      messageBuilder
          .append("Incorrect value for field '")
          .append(propertyPath.getLeafNode().getName())
          .append("': ")
          .append(violation.getMessage())
          .append("; ");
    }

    return messageBuilder.length() > 0
        ? messageBuilder
            .delete(messageBuilder.lastIndexOf("; "), messageBuilder.length())
            .toString()
        : "";
  }
}
