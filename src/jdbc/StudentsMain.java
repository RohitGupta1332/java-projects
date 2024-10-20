package jdbc;

public class StudentsMain {
    public static void main(String[] args) {
        StudentDataBase st = new StudentDataBase();
        st.insert();
        st.update(2, 90.0);
        st.retrieve();
    }
}
