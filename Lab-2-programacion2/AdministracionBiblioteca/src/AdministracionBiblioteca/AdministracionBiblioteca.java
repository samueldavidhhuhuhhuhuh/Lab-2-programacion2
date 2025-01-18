package AdministracionBiblioteca;
import java.time.LocalDateTime;
import java.util.*;


public class AdministracionBiblioteca {
    private Library library;
    private Scanner scanner;
    private Member currentMember;

    public AdministracionBiblioteca() {
        this.scanner = new Scanner(System.in);
        inicializarBiblioteca();
    }

    private void inicializarBiblioteca() {
        System.out.println("=== Configuración Inicial de la Biblioteca ===");
        System.out.print("Ingrese el nombre de la biblioteca: ");
        String nombreBiblioteca = scanner.nextLine();
        System.out.print("Ingrese la dirección de la biblioteca: ");
        String direccionBiblioteca = scanner.nextLine();

        this.library = new Library(nombreBiblioteca, direccionBiblioteca);
        System.out.println("\n¡Biblioteca creada exitosamente!");
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = obtenerOpcionUsuario();

            switch (opcion) {
                case 1:
                    registrarNuevoMiembro();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    agregarLibro();
                    break;
                case 4:
                    buscarLibros();
                    break;
                case 5:
                    verCatalogo();
                    break;
                case 6:
                    System.out.println("\n¡Gracias por usar el Sistema de Biblioteca!");
                    continuar = false;
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor intente nuevamente.");
            }
        }
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== Sistema de Biblioteca ===");
        if (currentMember != null) {
            System.out.println("Usuario actual: " + currentMember.getName());
        }
        System.out.println("1. Registrar nuevo miembro");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Agregar libro al catálogo");
        System.out.println("4. Buscar libros");
        System.out.println("5. Ver catálogo completo");
        System.out.println("6. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    private void mostrarMenuMiembro() {
        System.out.println("\n=== Menú de Miembro ===");
        System.out.println("1. Tomar prestado un libro");
        System.out.println("2. Devolver un libro");
        System.out.println("3. Ver mis préstamos actuales");
        System.out.println("4. Agregar reseña a un libro");
        System.out.println("5. Ver mis multas");
        System.out.println("6. Cerrar sesión");
        System.out.print("\nSeleccione una opción: ");
    }

    private int obtenerOpcionUsuario() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void registrarNuevoMiembro() {
        System.out.println("\n=== Registro de Nuevo Miembro ===");
        System.out.print("Ingrese nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese email: ");
        String email = scanner.nextLine();
        System.out.print("Ingrese dirección: ");
        String direccion = scanner.nextLine();

        Member nuevoMiembro = library.registerMember(nombre, email, direccion);
        System.out.println("\n¡Registro exitoso!");
        System.out.println("Su ID de miembro es: " + nuevoMiembro.getId());
        System.out.println("Por favor guarde este ID para iniciar sesión.");
    }

    private void iniciarSesion() {
        System.out.println("\n=== Inicio de Sesión ===");
        System.out.print("Ingrese su ID de miembro: ");
        String memberId = scanner.nextLine();

        Member member = library.getMembers().get(memberId);
        if (member != null) {
            currentMember = member;
            System.out.println("\n¡Bienvenido/a " + member.getName() + "!");
            gestionarSesionMiembro();
        } else {
            System.out.println("\nID de miembro no encontrado.");
        }
    }

    private void gestionarSesionMiembro() {
        boolean sesionActiva = true;
        while (sesionActiva) {
            mostrarMenuMiembro();
            int opcion = obtenerOpcionUsuario();

            switch (opcion) {
                case 1:
                    tomarLibroPrestado();
                    break;
                case 2:
                    devolverLibro();
                    break;
                case 3:
                    verPrestamosMiembro();
                    break;
                case 4:
                    agregarResena();
                    break;
                case 5:
                    verMultas();
                    break;
                case 6:
                    currentMember = null;
                    sesionActiva = false;
                    System.out.println("\nSesión cerrada exitosamente.");
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor intente nuevamente.");
            }
        }
    }

    private void agregarLibro() {
        System.out.println("\n=== Agregar Nuevo Libro ===");
        System.out.print("Ingrese título: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese autor: ");
        String autor = scanner.nextLine();
        System.out.print("Ingrese ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Ingrese género: ");
        String genero = scanner.nextLine();
        System.out.print("Ingrese número de copias a agregar: ");
        int copias = Integer.parseInt(scanner.nextLine());

        Book libro = library.addBook(titulo, autor, isbn, genero);
        for (int i = 0; i < copias; i++) {
            libro.addCopy();
        }

        System.out.println("\n¡Libro agregado exitosamente!");
        System.out.println("Se agregaron " + copias + " copias al sistema.");
    }

    private void buscarLibros() {
        System.out.println("\n=== Búsqueda de Libros ===");
        System.out.println("1. Buscar por título");
        System.out.println("2. Buscar por autor");
        System.out.println("3. Buscar por género");
        System.out.print("\nSeleccione método de búsqueda: ");

        int opcion = obtenerOpcionUsuario();
        System.out.print("Ingrese término de búsqueda: ");
        String termino = scanner.nextLine();

        List<Book> resultados = null;
        switch (opcion) {
            case 1:
                resultados = LibrarySearch.searchByTitle(library, termino);
                break;
            case 2:
                resultados = LibrarySearch.searchByAuthor(library, termino);
                break;
            case 3:
                resultados = LibrarySearch.searchByGenre(library, termino);
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }

        mostrarResultadosBusqueda(resultados);
    }

    private void mostrarResultadosBusqueda(List<Book> libros) {
        if (libros.isEmpty()) {
            System.out.println("\nNo se encontraron resultados.");
            return;
        }

        System.out.println("\nResultados encontrados:");
        for (int i = 0; i < libros.size(); i++) {
            Book libro = libros.get(i);
            System.out.println((i + 1) + ". " + libro.getTitle() + " - " + libro.getAuthor());
            System.out.println("   Género: " + libro.getGenre());
            System.out.println("   Copias disponibles: " +
                    libro.getCopies().stream()
                            .filter(copy -> copy.getStatus() == BookStatus.AVAILABLE)
                            .count());
            System.out.println("   Calificación promedio: " + libro.getAverageRating());
        }
    }

    private void verCatalogo() {
        System.out.println("\n=== Catálogo Completo ===");
        List<Book> todosLosLibros = new ArrayList<>(library.getBooks().values());
        mostrarResultadosBusqueda(todosLosLibros);
    }

    private void tomarLibroPrestado() {
        System.out.println("\n=== Tomar Libro Prestado ===");
        verCatalogo();
        System.out.print("\nIngrese el ID del libro que desea tomar prestado: ");
        String bookId = scanner.nextLine();

        Loan loan = library.borrowBook(bookId, currentMember.getId());
        if (loan != null) {
            System.out.println("\n¡Libro prestado exitosamente!");
            System.out.println("Fecha de devolución: " + loan.getDueDate());
        } else {
            System.out.println("\nNo se pudo realizar el préstamo. Verifique la disponibilidad del libro.");
        }
    }

    private void devolverLibro() {
        System.out.println("\n=== Devolver Libro ===");
        verPrestamosMiembro();

        if (!currentMember.getBorrowedBooks().isEmpty()) {
            System.out.print("\nIngrese el ID del préstamo a devolver: ");
            String loanId = scanner.nextLine();

            Fine fine = library.returnBook(loanId);
            if (fine != null) {
                System.out.println("\nLibro devuelto con retraso. Se ha generado una multa:");
                System.out.println("Monto: $" + fine.getAmount());
                System.out.println("Razón: " + fine.getReason());
            } else {
                System.out.println("\n¡Libro devuelto exitosamente!");
            }
        }
    }

    private void verPrestamosMiembro() {
        System.out.println("\n=== Mis Préstamos Actuales ===");
        List<Loan> prestamos = currentMember.getBorrowedBooks();

        if (prestamos.isEmpty()) {
            System.out.println("No tiene préstamos activos.");
            return;
        }

        for (Loan loan : prestamos) {
            if (loan.getReturnDate() == null) {
                Book book = findBookByLoan(loan);
                if (book != null) {
                    System.out.println("ID Préstamo: " + loan.getId());
                    System.out.println("Libro: " + book.getTitle());
                    System.out.println("Fecha de préstamo: " + loan.getLoanDate());
                    System.out.println("Fecha de devolución: " + loan.getDueDate());
                    System.out.println();
                }
            }
        }
    }

    private Book findBookByLoan(Loan loan) {
        return library.getBooks().values().stream()
                .filter(book -> book.getCopies().stream()
                        .anyMatch(copy -> copy.getId().equals(loan.getBookCopyId())))
                .findFirst()
                .orElse(null);
    }

    private void agregarResena() {
        System.out.println("\n=== Agregar Reseña ===");
        verCatalogo();
        System.out.print("\nIngrese el ID del libro que desea reseñar: ");
        String bookId = scanner.nextLine();

        Book book = library.getBooks().get(bookId);
        if (book != null) {
            System.out.print("Ingrese calificación (1-5): ");
            int rating = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese su comentario: ");
            String comment = scanner.nextLine();

            Review review = new Review(currentMember.getId(), rating, comment);
            book.addReview(review);
            System.out.println("\n¡Reseña agregada exitosamente!");
        } else {
            System.out.println("\nLibro no encontrado.");
        }
    }

    private void verMultas() {
        System.out.println("\n=== Mis Multas ===");
        List<Fine> multas = currentMember.getFines();

        if (multas.isEmpty()) {
            System.out.println("No tiene multas pendientes.");
            return;
        }

        for (Fine fine : multas) {
            System.out.println("Fecha: " + fine.getDateIssued());
            System.out.println("Monto: $" + fine.getAmount());
            System.out.println("Razón: " + fine.getReason());
            System.out.println("Estado: " + (fine.isPaid() ? "Pagada" : "Pendiente"));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        AdministracionBiblioteca sistema = new AdministracionBiblioteca();
        sistema.iniciar();
    }
}


enum LibraryCardStatus {
    ACTIVE, SUSPENDED, EXPIRED
}

enum BookStatus {
    AVAILABLE, BORROWED, RESERVED, MAINTENANCE
}

class Review {
    private final String id;
    private final String userId;
    private final int rating;
    private final String comment;
    private final LocalDateTime date;

    public Review(String userId, int rating, String comment) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.rating = Math.min(Math.max(rating, 1), 5);
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getDate() { return date; }
}

class BookCopy {
    private final String id;
    private final String bookId;
    private BookStatus status;
    private String condition;
    private String location;

    public BookCopy(String bookId) {
        this.id = UUID.randomUUID().toString();
        this.bookId = bookId;
        this.status = BookStatus.AVAILABLE;
        this.condition = "Good";
        this.location = "Main Shelf";
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getBookId() { return bookId; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}

class Book {
    private final String id;
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private List<Review> reviews;
    private List<BookCopy> copies;
    private double averageRating;

    public Book(String title, String author, String isbn, String genre) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.reviews = new ArrayList<>();
        this.copies = new ArrayList<>();
        this.averageRating = 0.0;
    }

    public BookCopy addCopy() {
        BookCopy copy = new BookCopy(this.id);
        copies.add(copy);
        return copy;
    }

    public void addReview(Review review) {
        reviews.add(review);
        updateAverageRating();
    }

    private void updateAverageRating() {
        if (!reviews.isEmpty()) {
            averageRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
        }
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
    public List<Review> getReviews() { return reviews; }
    public List<BookCopy> getCopies() { return copies; }
    public double getAverageRating() { return averageRating; }
}

class LibraryCard {
    private final String id;
    private final String memberId;
    private LibraryCardStatus status;
    private LocalDateTime expiryDate;
    private final LocalDateTime issuedDate;

    public LibraryCard(String memberId, LocalDateTime expiryDate) {
        this.id = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.status = LibraryCardStatus.ACTIVE;
        this.expiryDate = expiryDate;
        this.issuedDate = LocalDateTime.now();
    }

    public boolean isValid() {
        return status == LibraryCardStatus.ACTIVE &&
                expiryDate.isAfter(LocalDateTime.now());
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public LibraryCardStatus getStatus() { return status; }
    public void setStatus(LibraryCardStatus status) { this.status = status; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public LocalDateTime getIssuedDate() { return issuedDate; }
}

class Fine {
    private final String id;
    private final double amount;
    private final String reason;
    private final LocalDateTime dateIssued;
    private boolean isPaid;

    public Fine(double amount, String reason) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.reason = reason;
        this.dateIssued = LocalDateTime.now();
        this.isPaid = false;
    }

    public void pay() {
        this.isPaid = true;
    }

    // Getters
    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getReason() { return reason; }
    public LocalDateTime getDateIssued() { return dateIssued; }
    public boolean isPaid() { return isPaid; }
}

class Member {
    private final String id;
    private String name;
    private String email;
    private String address;
    private LibraryCard libraryCard;
    private List<Loan> borrowedBooks;
    private List<Reservation> reservations;
    private List<Fine> fines;

    public Member(String name, String email, String address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.fines = new ArrayList<>();
    }

    public void addFine(double amount, String reason) {
        Fine fine = new Fine(amount, reason);
        fines.add(fine);
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public LibraryCard getLibraryCard() { return libraryCard; }
    public void setLibraryCard(LibraryCard libraryCard) { this.libraryCard = libraryCard; }
    public List<Loan> getBorrowedBooks() { return borrowedBooks; }
    public List<Reservation> getReservations() { return reservations; }
    public List<Fine> getFines() { return fines; }
}

class Loan {
    private final String id;
    private final String bookCopyId;
    private final String memberId;
    private final LocalDateTime loanDate;
    private final LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private boolean isOverdue;

    public Loan(String bookCopyId, String memberId) {
        this.id = UUID.randomUUID().toString();
        this.bookCopyId = bookCopyId;
        this.memberId = memberId;
        this.loanDate = LocalDateTime.now();
        this.dueDate = loanDate.plusDays(14);
    }

    public void returnBook() {
        this.returnDate = LocalDateTime.now();
        this.isOverdue = returnDate.isAfter(dueDate);
    }

    // Getters
    public String getId() { return id; }
    public String getBookCopyId() { return bookCopyId; }
    public String getMemberId() { return memberId; }
    public LocalDateTime getLoanDate() { return loanDate; }
    public LocalDateTime getDueDate() { return dueDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public boolean isOverdue() { return isOverdue; }
}

class Reservation {
    private final String id;
    private final String bookId;
    private final String memberId;
    private final LocalDateTime reservationDate;
    private final LocalDateTime expiryDate;
    private String status;

    public Reservation(String bookId, String memberId) {
        this.id = UUID.randomUUID().toString();
        this.bookId = bookId;
        this.memberId = memberId;
        this.reservationDate = LocalDateTime.now();
        this.expiryDate = reservationDate.plusDays(7);
        this.status = "ACTIVE";
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getBookId() { return bookId; }
    public String getMemberId() { return memberId; }
    public LocalDateTime getReservationDate() { return reservationDate; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

class NotificationService {
    public void sendOverdueNotice(Member member, Loan loan) {
        // Simulación de envío de notificación
        System.out.println("Overdue notice sent to " + member.getEmail() +
                " for loan " + loan.getId());
    }

    public void sendReservationAvailable(Member member, Book book) {
        System.out.println("Reservation available notice sent to " +
                member.getEmail() + " for book " + book.getTitle());
    }
}

class FineCalculator {
    private static final double DAILY_RATE = 1.0; // $1 per day

    public static double calculateOverdueFine(Loan loan) {
        if (loan.getReturnDate() == null || !loan.isOverdue()) {
            return 0.0;
        }
        long daysOverdue = java.time.Duration.between(
                loan.getDueDate(), loan.getReturnDate()).toDays();
        return daysOverdue * DAILY_RATE;
    }
}

class Library {
    private String name;
    private String address;
    private Map<String, Book> books;
    private Map<String, Member> members;
    private Map<String, Loan> loans;
    private Map<String, Reservation> reservations;
    private NotificationService notificationService;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.loans = new HashMap<>();
        this.reservations = new HashMap<>();
        this.notificationService = new NotificationService();
    }

    public Book addBook(String title, String author, String isbn, String genre) {
        Book book = new Book(title, author, isbn, genre);
        books.put(book.getId(), book);
        return book;
    }

    public Member registerMember(String name, String email, String address) {
        Member member = new Member(name, email, address);
        LocalDateTime expiryDate = LocalDateTime.now().plusYears(1);
        member.setLibraryCard(new LibraryCard(member.getId(), expiryDate));
        members.put(member.getId(), member);
        return member;
    }

    public Loan borrowBook(String bookId, String memberId) {
        Book book = books.get(bookId);
        Member member = members.get(memberId);

        if (book == null || member == null ||
                member.getLibraryCard() == null ||
                !member.getLibraryCard().isValid()) {
            return null;
        }

        Optional<BookCopy> availableCopy = book.getCopies().stream()
                .filter(copy -> copy.getStatus() == BookStatus.AVAILABLE)
                .findFirst();

        if (!availableCopy.isPresent()) {
            return null;
        }

        BookCopy copy = availableCopy.get();
        copy.setStatus(BookStatus.BORROWED);

        Loan loan = new Loan(copy.getId(), memberId);
        loans.put(loan.getId(), loan);
        member.getBorrowedBooks().add(loan);

        return loan;
    }

    public Fine returnBook(String loanId) {
        Loan loan = loans.get(loanId);
        if (loan == null || loan.getReturnDate() != null) {
            return null;
        }

        loan.returnBook();
        Member member = members.get(loan.getMemberId());

        // Update book copy status
        books.values().stream()
                .flatMap(book -> book.getCopies().stream())
                .filter(copy -> copy.getId().equals(loan.getBookCopyId()))
                .findFirst()
                .ifPresent(copy -> copy.setStatus(BookStatus.AVAILABLE));

        if (loan.isOverdue()) {
            double fineAmount = FineCalculator.calculateOverdueFine(loan);
            member.addFine(fineAmount, "Overdue book return");
            return member.getFines().get(member.getFines().size() - 1);
        }
        return null;
    }

    public void checkOverdueLoans() {
        LocalDateTime currentDate = LocalDateTime.now();
        loans.values().stream()
                .filter(loan -> loan.getReturnDate() == null &&
                        currentDate.isAfter(loan.getDueDate()))
                .forEach(loan -> {
                    Member member = members.get(loan.getMemberId());
                    notificationService.sendOverdueNotice(member, loan);
                });
    }

    // Getters
    public String getName() { return name; }
    public String getAddress() { return address; }
    public Map<String, Book> getBooks() { return books; }
    public Map<String, Member> getMembers() { return members; }
    public Map<String, Loan> getLoans() { return loans; }
    public Map<String, Reservation> getReservations() { return reservations; }
}

class LibrarySearch {
    public static List<Book> searchByTitle(Library library, String title) {
        return library.getBooks().values().stream()
                .filter(book -> book.getTitle().toLowerCase()
                        .contains(title.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    public static List<Book> searchByAuthor(Library library, String author) {
        return library.getBooks().values().stream()
                .filter(book -> book.getAuthor().toLowerCase()
                        .contains(author.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }

    public static List<Book> searchByGenre(Library library, String genre) {
        return library.getBooks().values().stream()
                .filter(book -> book.getGenre().toLowerCase()
                        .contains(genre.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }
}