![logo_ironhack_blue 7](https://user-images.githubusercontent.com/23629340/40541063-a07a0a8a-601a-11e8-91b5-2f13e4e6b441.png)

# LAB Java | Advanced DI & Postman Power-Up

## Introduction

In this lab, you will build a more advanced Spring Boot application that conditionally enables a new feature called **EarlyBirdDiscountService**. This service will calculate a discount for early bookings based on configurable criteria. You will then create a series of automated Postman tests to verify the behavior of your endpoint under different conditions. The lab challenges you to integrate advanced dependency injection techniques with dynamic API test automation—pushing you to design and test a feature beyond the basic examples provided in class.

<br />

## Requirements

1. Fork this repo.
2. Clone this repo.
3. Add your instructor and the class graders as collaborators to your repository. If you are unsure who your class graders are, ask your instructor or refer to the day 1 slide deck.
4. In the repository, create a Java project and add the code for the following prompts.

<br />

## Submission

Once you finish the assignment, submit a URL link to your repository or your pull request in the field below.

<br />

## Instructions

### 1. Build the Spring Boot Application

- **Conditional Bean Setup:**

  - Create a configuration class (e.g., `DiscountFeatureConfig`) that defines a bean for a service named **EarlyBirdDiscountService**.
  - Configure the bean with `@ConditionalOnProperty` so that it is loaded only when a property (e.g., `feature.earlybird.enabled`) is set to `true`.

- **Service Implementation:**

  - Design the **EarlyBirdDiscountService** to include a method that calculates a discount percentage. The discount should be determined by the booking date relative to the event date (for example, bookings made at least 30 days in advance receive a 15% discount).
  - The service should handle various scenarios (e.g., no discount if the booking is too late) and return an appropriate message or discount value.

- **REST Controller:**

  - Develop a REST controller (e.g., `DiscountController`) that uses constructor-based DI to inject the **EarlyBirdDiscountService**.
  - Expose an endpoint (e.g., `GET /api/discount`) that:
    - Accepts query parameters for the event date and booking date.
    - Returns the calculated discount if the service is active.
    - Handles the scenario gracefully when the service is not enabled (e.g., returns a suitable error message or HTTP status).

- **Application Configuration:**
  - In your `application.properties` file, add the property `feature.earlybird.enabled` and set it to either `true` or `false`.

<br />

### 2. Develop and Automate Postman Tests

- **Environment Setup:**

  - Create a new Postman environment named “AdvancedPromoEnv.”
  - Define a variable `baseUrl` with the value pointing to your Spring Boot application (e.g., `http://localhost:8080`).

- **Collection Creation:**

  - Create a Postman collection titled “Advanced DI & Early Bird Discount Tests.”

- **Design the Following Requests:**

  - **GET Request (Calculate Discount):**
    - **Method:** GET
    - **URL:** `{{baseUrl}}/api/discount`
    - **Query Parameters:** Include parameters for `eventDate` and `bookingDate` (e.g., `eventDate=2025-06-30`, `bookingDate=2025-05-15`).
    - **Goal:** Verify that when `feature.earlybird.enabled` is `true`, the endpoint returns the correct discount message or value.
  - **Negative Test Request:**
    - Change the query parameters (e.g., booking closer to the event date) to test the edge cases, verifying that no discount is applied when appropriate.

- **Add Scripts:**

  - **Pre-request Script:** (Optional) Log a message to indicate that the request is starting.
  - **Post-response Script:** Write a script that validates:
    - The response status code.
    - That the response contains a discount value or message consistent with your business rules.
    - Optionally, log key output for debugging purposes.

- **Run and Validate:**
  - Use the Postman Collection Runner to execute your requests.
  - Test both the positive (discount applied) and negative (no discount) scenarios by toggling the property `feature.earlybird.enabled` and by varying the query parameters.
  ![Collection Postman.PNG](Collection%20Postman.PNG)
<br />

### 3. Documentation in README.md

Update your repository’s `README.md` to include:

- A brief explanation of your design choices for the **EarlyBirdDiscountService**.

  He hecho un sistema que da diferentes descuentos según cuántos días antes reserves, siendo un 15%
  si reservas con más de 30 días hasta no tener descuento si reservas en el plazo de menos de 7 días.
  El código comprueba la diferencia entre fecha de la reserva y el evento en cuestión para así ejecutar
  el descuento correspondiente.

- Answers to:


  - Why did you choose constructor-based DI for this lab?

    He usado inyección por constructor porque creo que es mas fácil pasar todo
    lo que necesita mi servicio desde el principio.
  

  - What advantages do Postman pre-request and post-response scripts offer for automated testing?
    
    Los scripts de pre-solicitud me han servido para preparar los datos de forma automática antes de cada prueba.
    Y los post-respuesta me han ahorrado el tiempo ya que al verificar de forma automática si se aplicaban los descuentos
    correctamente , me ha evitado revisar todo de forma manual.
  

  - How does your application behave when the early bird feature is disabled?

    La aplicación sigue funcionando normal, lo único que varia es que no da descuento a nadie, sin importar
    en que momento reserven, Asi evitamos que la app se rompa cuando queremos quitar las promociones de forma temporal.
  

  - What are some challenges you faced when integrating advanced DI with API testing?

    A la hora de asegurarme que los componentes estuvieran bien conectados entre sí, ya que si falla algo,
    es complicado saber donde puede estar el problema. El tema de las fechas diferentes para cada tipo de prueba tambien
    tiene su dificultad.

<br />

## FAQs

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">I am stuck and don't know how to solve the problem or where to start. What should I do?</summary>

<br> <!-- ✅ -->

If you are stuck in your code and don't know how to solve the problem or where to start, you should take a step back and try to form a clear, straight forward question about the specific issue you are facing. The process you will go through while trying to define this question, will help you narrow down the problem and come up with potential solutions.

For example, are you facing a problem because you don't understand the concept or are you receiving an error message that you don't know how to fix? It is usually helpful to try to state the problem as clearly as possible, including any error messages you are receiving. This can help you communicate the issue to others and potentially get help from classmates or online resources.

Once you have a clear understanding of the problem, you should be able to start working toward the solution.

</details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">How do I create a Spring boot project?</summary>

<br> <!-- ✅ -->

Spring boot is a framework for creating stand-alone, production-grade applications that are easy to launch and run. The best way to create a Spring boot project is to use the Spring Initializer website. The website provides a convenient way to generate a basic project structure with all the necessary dependencies and configurations.

- Step 1: Go to [start.spring.io](https://start.spring.io/)
- Step 2: Choose the type of project you want to create, such as Maven or Gradle.
- Step 3: Select the version of Spring Boot you want to use.
- Step 4: Choose the dependencies you need for your project. Some common dependencies include web, jpa and data-jpa.
- Step 5: Click the "Generate" button to download the project files.

Alternatively, you can use an Integrated Development Environment (IDE) such as Eclipse or IntelliJ IDEA. These IDEs have plugins for creating Spring boot projects, making it easy to set up the environment and get started with coding.

 </details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">What if my Postman tests don't pass?</summary>

<br> <!-- ✅ -->

- Ensure your `baseUrl` environment variable is correctly set.
- Verify that your application is running and accessible.
- Check your pre-request and post-response scripts for typos or logical errors.
- Use the Postman Console (View > Show Postman Console) to debug and view logs.

  <br>

</details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">How do I debug issues with conditional bean loading?</summary>
  
  <br />
  
  - Check your `application.properties` to ensure the correct values are set.
  - Use logging in your configuration and service classes to confirm whether beans are being instantiated.
  - Review your IDE’s console output and Spring Boot startup logs for any conditional configuration warnings or errors.
  
  <br />
</details>

<br>

<details>
  <summary style="font-size: 16px; cursor: pointer; outline: none; font-weight: bold;">I am unable to push changes to my repository. What should I do?</summary>

<br> <!-- ✅ -->

If you are unable to push changes to your repository, here are a few steps that you can follow:

1. Check your internet connection: Ensure that your internet connection is stable and working.
1. Verify your repository URL: Make sure that you are using the correct repository URL to push your changes.
1. Check Git credentials: Ensure that your Git credentials are up-to-date and correct. You can check your credentials using the following command:

```bash
git config --list
```

4. Update your local repository: Before pushing changes, make sure that your local repository is up-to-date with the remote repository. You can update your local repository using the following command:

```bash
git fetch origin
```

5. Check for conflicts: If there are any conflicts between your local repository and the remote repository, resolve them before pushing changes.
6. Push changes: Once you have resolved any conflicts and updated your local repository, you can try pushing changes again using the following command:

```bash
git push origin <branch_name>
```

</details>
