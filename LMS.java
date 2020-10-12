import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableColumnModel;

import java.awt.Font;
import java.awt.Button;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class LMS {
	private DataBase db;
	private JFrame frmLms;
	private JLayeredPane lpMain;

	private JPanel pnlLogin;

	private JPanel pnlWorkingArea;

	private JLayeredPane lpFeatures;
	private JPanel pnlTimetable;
	private JPanel pnlConflicts;
	private JPanel pnlProfile;
	private JPanel pnlStudTeachTimetable;
	private JPanel pnlClassTimetable;
	private JPanel pnlClassList;
	private JPanel pnlStudentList;
	private JPanel pnlSlavesInfo;
	private JPanel pnlRemoveSlave;
	private JPanel pnlSetCalssSlave;
	private JPanel pnlPeopleInfo;
	private JPanel pnlAddClass;
	private JPanel pnlRemoveClass;
	private JPanel pnlSetTeacher;
	private JPanel pnlRemoveMember;

	private JPanel pnlDefault;

	private JPanel pnlFeatureButtons;
	private JButton btnTimetable;
	private JButton btnConflicts;
	private JButton btnProfile;
	private JButton btnStudTeachTimetable;
	private JButton btnClassTimetable;
	private JButton btnClassesList;
	private JButton btnStudentList;
	private JButton btnSlaveInfo;
	private JButton btnRemoveSlave;
	private JButton btnSetClassSlave;
	private JButton btnPeopleInfo;
	private JButton btnAddClass;
	private JButton btnRemoveClass;
	private JButton btnSetTeacher;
	private JButton btnRemoveMember;
	private JButton btnStudentProfle;
	private JButton btnCreateCabinet;
	private JButton btnExit;

	private JTextField fldLogin;
	private JPasswordField fldPassword;
	private JLabel lblError;
	private JTextField fldName;
	private JButton btnShow;
	private JLabel lblInput_error;
	private JTextField fldCabinetNumber;
	private JComboBox cbBuilding;
	private JButton btnShow_tt;
	private JTable table;
	private JTextField fldClass_ID;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField fldLogin_1;
	private JTextField fldClassID_1;
	private JButton btnRemove_1;
	private JTextField fldLogin_3;
	private JTextField fldClassID_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JTextField fldClassName;
	private JTextField fldClass;
	private JTextField fldClassID;
	private JTextField fldEmail;
	private JTextField fldClassID_2;
	private JTextField fldStudent;
	private JTextField fldTeacher;
	private JTextField fldCabinet;
	private JTextField fldCabinet_9;
	private JTextField fldPassport;
	private JTextField fldMial;
	private JTextField fldFIO;
	private JTextField fldMail_Tutor;
	private JLabel lblerror;
	private JLabel lblerror_1;
	private JLabel lblerror_2;
	private JLabel lblerror_5;
	private JLabel lblerror_23;
	private JLabel lblerror_8;
	private String login = "";
	private int type = 0;
	private JScrollPane spStudentTeacherTimetable;
	private JScrollPane spClassTimetable;
	private JTextField fldName_3;
	private JLabel lblNewLabel_10;
	private JTextField fldLogin_9;
	private JLabel lblNewLabel_12;
	private JLabel lblNewLabel_13;
	private JScrollPane spStudentList;
	private JTextField fldDate;
	private JTextField fldTime;
	private JComboBox cbBuilding_1;
	private JLabel lblerror_16;
	private JLabel lblNewLabel_18;
	private JTextField fldTeacher_1;
	private JLabel lblerror_22;
	private JLabel lblerror_21;
	private JComboBox cbStudTeach;
	private JButton btnGetPasswords;
	private JComboBox cbStudTeach_1;
	private JComboBox cbSort;
	private JCheckBox cbSupervisor;
	private JComboBox cbSelectTS;
	private JLabel lblerror_40;
	private JLabel lblNO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LMS window = new LMS();
					window.frmLms.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LMS() {
		db = new DataBase();
		initialize();
	}

	public void switchPanels(JLayeredPane layer, JPanel panel) {
		layer.removeAll();
		layer.add(panel);
		layer.repaint();
		layer.revalidate();
	}

	public void exit() {
		login = "";
	}

	public void addFeatures(int type) {
		// TODO добавить кнопок и расположение
		JButton[] SupervisorFeatureButtons = { btnTimetable, btnConflicts, btnProfile, btnStudTeachTimetable,
				btnClassTimetable, btnClassesList, btnStudentList, btnRemoveMember, btnGetPasswords, btnPeopleInfo,
				btnStudentProfle, btnCreateCabinet, btnAddClass, btnRemoveClass, btnSetClassSlave, btnRemoveSlave,
				btnSetTeacher, btnExit };

		JButton[] StudentFeatureButtons = { btnTimetable, btnConflicts, btnProfile, btnStudTeachTimetable,
				btnClassTimetable, btnClassesList, btnStudentList, btnExit };

		JButton[] TeacherFeatureButtons = { btnTimetable, btnConflicts, btnProfile, btnStudTeachTimetable,
				btnClassTimetable, btnClassesList, btnStudentList, btnSlaveInfo, btnRemoveSlave, btnSetClassSlave,
				btnExit };
		String[] StudentTexts = { "Моё расписание", "Конфликты в расписании", "Профиль", "Расписание сотруника",
				"Расписание кабинета", "Список занятий", "Сотрудники на занятии", "Выйти и системы" };
		String[] TeacherTexts = { "Моё расписание", "Конфликты в расписании", "Профиль", "Расписание сотруника",
				"Расписание кабинета", "Список занятий", "Сотрудники на занятии", "Информация о подопечном",
				"Удалить с занятия", "Назначит занятие", "Выйти и системы" };
		String[] SuperviserTexts = { "Моё расписание", "Конфликты в расписании", "Профиль", "Расписание сотруника",
				"Расписание кабинета", "Список занятий", "Сотрудники на занятии", "Удалить объект",
				"Пароли пользователей", "Информация о пользователях", "Редактор пользователей", "Редактор кабинетов",
				"Создать занятие", "Удалить занятие", "Назначить занятие учащемуся", "Удалить учащегося с занятия",
				"Назначить преподавателя", "Выйти и системы" };
		pnlFeatureButtons.setBounds(0, 0, frmLms.getWidth(), frmLms.getHeight() / 3);
		pnlFeatureButtons.setBackground(Color.DARK_GRAY);
		lpFeatures.setBounds(0, frmLms.getHeight() / 3, frmLms.getWidth(), 2 * frmLms.getHeight() / 3);

		for (Component comp : lpFeatures.getComponents()) {
			comp.setBounds(0, 0, lpFeatures.getWidth(), lpFeatures.getHeight());
			comp.setBackground(Color.LIGHT_GRAY);
		}
		JButton[] features;
		String[] texts;
		int w_offset = 5;
		int h_offset = 5;
		int width = pnlFeatureButtons.getWidth() / 4 - 2 * w_offset;
		int height = pnlFeatureButtons.getHeight() / 5 - 2 * h_offset;
		pnlFeatureButtons.removeAll();
		// type = 3;
		if (type == 1) {
			features = StudentFeatureButtons;
			texts = StudentTexts;
		} else if (type == 2) {
			features = TeacherFeatureButtons;
			texts = TeacherTexts;
		} else {
			features = SupervisorFeatureButtons;
			texts = SuperviserTexts;
		}
		for (int i = 0; i < features.length; i++) {
			features[i].setBounds(w_offset + i % 4 * (width + w_offset), h_offset + i / 4 * (height + h_offset), width,
					height);
			features[i].setText(texts[i]);
			features[i].setForeground(Color.BLACK);
			features[i].setFont(new Font("Tahoma", Font.BOLD, 11));
			pnlFeatureButtons.add(features[i]);

		}
		pnlFeatureButtons.repaint();
		pnlFeatureButtons.revalidate();
	}

	public void fill_table(JTable table, String request) {

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLms = new JFrame();
		frmLms.setBounds(new Rectangle(0, 0, 900, 550));
		frmLms.setResizable(false);
		frmLms.setTitle("LMS");
		frmLms.setBounds(100, 100, 900, 550);
		frmLms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLms.getContentPane().setLayout(new BorderLayout(0, 0));

		lpMain = new JLayeredPane();
		frmLms.getContentPane().add(lpMain, BorderLayout.CENTER);
		lpMain.setLayout(new CardLayout(0, 0));

		pnlLogin = new JPanel();
		lpMain.add(pnlLogin, "name_189362071419400");

		JLabel lblLogin = new JLabel("\u041B\u043E\u0433\u0438\u043D:");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLogin.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblPassword = new JLabel("\u041F\u0430\u0440\u043E\u043B\u044C:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);

		fldLogin = new JTextField();
		fldLogin.setColumns(10);

		fldPassword = new JPasswordField();
		fldPassword.setColumns(10);

		JButton btnEnter = new JButton("\u0412\u0445\u043E\u0434");
		btnEnter.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// U0_login_and_password_check(IN email VARCHAR(45), IN password_ CHAR(8), OUT
				// result_status TINYINT)
				// user :"apchikov@mybase.ru"
				// password : 53713069
				String name = fldLogin.getText();
				String password = new String(fldPassword.getPassword());
				String procedure = "U0_login_and_password_check";
				String[] names = { "email", "password_" };
				String[] types = { "String", "String" };
				String[] values = { name, password };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (code == 0)
						lblError.setVisible(true);
					else {
						login = name;
						type = code;
						lblError.setVisible(false);
						fldLogin.setText("");
						fldPassword.setText("");
						addFeatures(code);
						switchPanels(lpMain, pnlWorkingArea);
						switchPanels(lpFeatures, pnlDefault);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		lblError = new JLabel(
				"\u041D\u0435\u0432\u0435\u0440\u043D\u044B\u0439 \u043B\u043E\u0433\u0438\u043D \u0438\u043B\u0438 \u043F\u0430\u0440\u043E\u043B\u044C!");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblError.setForeground(Color.RED);
		lblError.setVisible(false);

		JLabel lblNewLabel_6 = new JLabel("\u0410\u0432\u0442\u043E\u0440\u0438\u0437\u0430\u0446\u0438\u044F:");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 20));
		GroupLayout gl_pnlLogin = new GroupLayout(pnlLogin);
		gl_pnlLogin.setHorizontalGroup(gl_pnlLogin.createParallelGroup(Alignment.TRAILING).addGroup(gl_pnlLogin
				.createSequentialGroup().addGap(311)
				.addGroup(gl_pnlLogin.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_6, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
						.addComponent(btnEnter, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
						.addGroup(gl_pnlLogin.createSequentialGroup()
								.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(fldLogin, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlLogin.createSequentialGroup()
								.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(fldPassword, GroupLayout.PREFERRED_SIZE, 174,
										GroupLayout.PREFERRED_SIZE)))
				.addGap(290))
				.addGroup(gl_pnlLogin.createSequentialGroup().addContainerGap(361, Short.MAX_VALUE)
						.addComponent(lblError, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addGap(337)));
		gl_pnlLogin.setVerticalGroup(gl_pnlLogin.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLogin.createSequentialGroup().addGap(132).addComponent(lblNewLabel_6).addGap(36)
						.addGroup(gl_pnlLogin.createParallelGroup(Alignment.BASELINE)
								.addComponent(fldLogin, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addGap(45)
						.addGroup(
								gl_pnlLogin.createParallelGroup(Alignment.BASELINE)
										.addComponent(fldPassword, GroupLayout.PREFERRED_SIZE, 30,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPassword))
						.addGap(28)
						.addComponent(lblError, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(18).addComponent(btnEnter, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addGap(130)));
		pnlLogin.setLayout(gl_pnlLogin);

		pnlWorkingArea = new JPanel();
		pnlWorkingArea.setLayout(null);
		lpMain.add(pnlWorkingArea, "name_189405765305100");

		lpFeatures = new JLayeredPane();
		lpFeatures.setBounds(10, 241, 876, 271);
		pnlWorkingArea.add(lpFeatures);
		lpFeatures.setLayout(new CardLayout(0, 0));

		pnlTimetable = new JPanel();
		pnlTimetable.setLayout(null);
		lpFeatures.add(pnlTimetable, "name_189919841249800");

		JScrollPane spTimetable = new JScrollPane();
		spTimetable.setBounds(80, 25, 700, 200);
		pnlTimetable.add(spTimetable);

		table = new JTable();
		spTimetable.setViewportView(table);

		lblNO = new JLabel(
				"\u0417\u0430\u043D\u044F\u0442\u0438\u044F \u043E\u0442\u0441\u0443\u0442\u0441\u0442\u0432\u0443\u044E\u0442!");
		lblNO.setHorizontalAlignment(SwingConstants.CENTER);
		lblNO.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNO.setBounds(260, 120, 379, 25);
		pnlTimetable.add(lblNO);

		pnlConflicts = new JPanel();
		pnlConflicts.setLayout(null);
		lpFeatures.add(pnlConflicts, "name_189919855419100");

		JScrollPane spConflicts = new JScrollPane();
		spConflicts.setBounds(80, 25, 700, 200);
		pnlConflicts.add(spConflicts);

		JLabel lblNewLabel_28 = new JLabel(
				"\u041A\u043E\u043D\u0444\u043B\u0438\u043A\u0442\u044B \u043E\u0442\u0441\u0443\u0442\u0441\u0442\u0432\u0443\u044E\u0442!");
		lblNewLabel_28.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_28.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_28.setForeground(Color.GREEN);
		lblNewLabel_28.setBounds(280, 120, 291, 25);
		pnlConflicts.add(lblNewLabel_28);

		pnlProfile = new JPanel();
		pnlProfile.setLayout(null);
		lpFeatures.add(pnlProfile, "name_189919868237200");

		JScrollPane spProfile = new JScrollPane();
		spProfile.setBounds(169, 31, 550, 197);
		pnlProfile.add(spProfile);

		pnlStudTeachTimetable = new JPanel();
		pnlStudTeachTimetable.setLayout(null);
		lpFeatures.add(pnlStudTeachTimetable, "name_189919878071100");

		fldName = new JTextField();
		fldName.setHorizontalAlignment(SwingConstants.CENTER);
		fldName.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldName.setColumns(10);
		fldName.setBounds(324, 31, 292, 25);
		pnlStudTeachTimetable.add(fldName);

		btnShow = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C");
		btnShow.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO таблица
				// U1_get_personal_timetable(IN email CHAR(45))
				String name = fldName.getText();
				String procedure = "U1_get_personal_timetable";
				String[] names = { "email" };
				String[] types = { "String" };
				String[] values = { name };

				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (code == 0) {
						lblInput_error.setVisible(true);
						spStudentTeacherTimetable.setVisible(false);
					} else {
						lblInput_error.setVisible(false);
						JTable table = fill_table(response);
						table.setEnabled(false);
						spStudentTeacherTimetable.setViewportView(table);
						spStudentTeacherTimetable.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				spStudentTeacherTimetable.repaint();
			}
		});
		btnShow.setBounds(639, 31, 118, 25);
		pnlStudTeachTimetable.add(btnShow);

		lblInput_error = new JLabel(
				"\u041F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044C \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D!");
		lblInput_error.setHorizontalAlignment(SwingConstants.CENTER);
		lblInput_error.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInput_error.setForeground(Color.RED);
		lblInput_error.setBounds(334, 67, 262, 25);
		lblInput_error.setVisible(false);
		pnlStudTeachTimetable.add(lblInput_error);

		JLabel lblInputName = new JLabel(
				"\u041B\u043E\u0433\u0438\u043D \u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044F:");
		lblInputName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInputName.setBounds(125, 31, 189, 25);
		pnlStudTeachTimetable.add(lblInputName);

		spStudentTeacherTimetable = new JScrollPane();
		spStudentTeacherTimetable.setBounds(94, 103, 700, 150);
		pnlStudTeachTimetable.add(spStudentTeacherTimetable);

		pnlClassTimetable = new JPanel();
		pnlClassTimetable.setLayout(null);
		lpFeatures.add(pnlClassTimetable, "name_189919888166300");

		fldCabinetNumber = new JTextField();
		fldCabinetNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldCabinetNumber.setHorizontalAlignment(SwingConstants.CENTER);
		fldCabinetNumber.setColumns(10);
		fldCabinetNumber.setBounds(303, 27, 176, 25);
		pnlClassTimetable.add(fldCabinetNumber);

		cbBuilding = new JComboBox();
		cbBuilding.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbBuilding.setModel(
				new DefaultComboBoxModel(new String[] { "\u041A\u043E\u0440\u043F\u0443\u0441", "\u0410", "\u0411" }));
		cbBuilding.setSelectedIndex(0);
		cbBuilding.setBounds(489, 27, 87, 25);
		pnlClassTimetable.add(cbBuilding);

		btnShow_tt = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C");
		btnShow_tt.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnShow_tt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO графика
				// U2_get_room_timetable(IN room_building VARCHAR(1), IN room_number VARCHAR(5))
				String number = fldCabinetNumber.getText();
				String corpus = (String) cbBuilding.getSelectedItem();
				if (corpus == "Корпус") {
					lblerror.setText("Выберете корпус!");
					lblerror.setVisible(true);
				} else {
					String procedure = "U2_get_room_timetable";
					String[] names = { "room_building", "room_number" };
					String[] types = { "String", "String" };
					String[] values = { corpus, number };
					try {
						ResultSet response = db.request(procedure, names, types, values);
						int code = db.getStatus();
						if (code == 0) {
							lblerror.setText("Расписание занятий для кабинета не найдено!");
							lblerror.setForeground(Color.RED);
							lblerror.setVisible(true);
							spClassTimetable.setVisible(false);
						} else {
							lblerror.setVisible(false);
							JTable table = fill_table(response);
							table.setEnabled(false);
							spClassTimetable.setViewportView(table);
							spClassTimetable.setVisible(true);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnShow_tt.setBounds(606, 27, 115, 25);
		pnlClassTimetable.add(btnShow_tt);

		lblerror = new JLabel("\u041A\u043B\u0430\u0441\u0441 \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D!");
		lblerror.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror.setForeground(Color.RED);
		lblerror.setBounds(169, 63, 459, 25);
		pnlClassTimetable.add(lblerror);

		spClassTimetable = new JScrollPane();
		spClassTimetable.setBounds(157, 99, 392, 141);
		pnlClassTimetable.add(spClassTimetable);

		JLabel lblNewLabel_14 = new JLabel(
				"\u041D\u043E\u043C\u0435\u0440 \u043A\u0430\u0431\u0438\u043D\u0435\u0442\u0430:");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_14.setBounds(146, 27, 159, 25);
		pnlClassTimetable.add(lblNewLabel_14);

		pnlClassList = new JPanel();
		pnlClassList.setLayout(null);
		lpFeatures.add(pnlClassList, "name_189919897580700");

		JScrollPane spClassList = new JScrollPane();
		spClassList.setBounds(80, 30, 700, 200);
		pnlClassList.add(spClassList);

		pnlStudentList = new JPanel();
		lpFeatures.add(pnlStudentList, "name_204217712864500");
		pnlStudentList.setLayout(null);

		fldClass_ID = new JTextField();
		fldClass_ID.setHorizontalAlignment(SwingConstants.CENTER);
		fldClass_ID.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldClass_ID.setBounds(298, 30, 172, 25);
		pnlStudentList.add(fldClass_ID);
		fldClass_ID.setColumns(10);

		JButton btnShow_st = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C");
		btnShow_st.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnShow_st.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO положение таблицы
				// U4_get_class_tutors_info(IN class_id INT, OUT result_status TINYINT)
				// U7_get_class_students_info(IN class_id INT, OUT result_status TINYINT)
				String id = fldClass_ID.getText();
				String who = (String) cbStudTeach.getSelectedItem();
				String procedure;
				if (who.equals("Студенты"))
					procedure = "U7_get_class_students_info";
				else
					procedure = "U4_get_class_tutors_info";
				String[] names = { "class_id" };
				String[] types = { "Int" };
				String[] values = { id };
				try {
					ResultSet response = db.request(procedure, names, types, values);

					int code = db.getStatus();
					if (code == 0) {
						lblerror_1.setVisible(true);
						spStudentList.setVisible(false);
					} else {
						lblerror_1.setVisible(false);
						JTable table = fill_table(response);
						table.setEnabled(false);
						spStudentList.setViewportView(table);
						spStudentList.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnShow_st.setBounds(665, 30, 114, 25);
		pnlStudentList.add(btnShow_st);

		JLabel lblNewLabel = new JLabel(
				"\u0418\u0434\u0435\u043D\u0442\u0438\u0444\u0438\u043A\u0430\u0442\u043E\u0440 \u0437\u0430\u043D\u044F\u0442\u0438\u044F:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(74, 30, 218, 25);
		pnlStudentList.add(lblNewLabel);

		lblerror_1 = new JLabel(
				"\u0417\u0430\u043D\u044F\u0442\u0438\u0435 \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u043E!");
		lblerror_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_1.setForeground(Color.RED);
		lblerror_1.setBounds(266, 77, 246, 19);
		pnlStudentList.add(lblerror_1);

		spStudentList = new JScrollPane();
		spStudentList.setBounds(74, 107, 705, 150);
		pnlStudentList.add(spStudentList);

		cbStudTeach = new JComboBox();
		cbStudTeach.setModel(new DefaultComboBoxModel(new String[] { "\u0421\u0442\u0443\u0434\u0435\u043D\u0442\u044B",
				"\u041F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u0438" }));
		cbStudTeach.setSelectedIndex(0);
		cbStudTeach.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbStudTeach.setBounds(492, 30, 163, 25);
		pnlStudentList.add(cbStudTeach);

		pnlSlavesInfo = new JPanel();
		lpFeatures.add(pnlSlavesInfo, "name_205535522498700");
		pnlSlavesInfo.setLayout(null);

		JScrollPane spSlavesInfo = new JScrollPane();
		spSlavesInfo.setBounds(172, 102, 510, 150);
		pnlSlavesInfo.add(spSlavesInfo);

		JButton btnShow_3 = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C");
		btnShow_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnShow_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO table
				// T1_get_info_on_related_students(IN email VARCHAR(45))
				String name = fldName_3.getText();
				String procedure = "T1_get_info_on_related_students";
				String[] names = { "email" };
				String[] types = { "String" };
				String[] values = { name };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (code == 0) {
						lblInput_error.setVisible(true);
					} else {
						lblInput_error.setVisible(false);
						JTable table = fill_profile(response);
						table.setEnabled(false);
						spSlavesInfo.setViewportView(table);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnShow_3.setBounds(569, 30, 113, 25);
		pnlSlavesInfo.add(btnShow_3);

		fldName_3 = new JTextField();
		fldName_3.setHorizontalAlignment(SwingConstants.CENTER);
		fldName_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldName_3.setBounds(349, 30, 201, 25);
		pnlSlavesInfo.add(fldName_3);
		fldName_3.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel(
				"\u041B\u043E\u0433\u0438\u043D \u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044F:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_8.setBounds(172, 30, 201, 25);
		pnlSlavesInfo.add(lblNewLabel_8);

		JLabel lblerror_15 = new JLabel(
				"\u041F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044C \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D!");
		lblerror_15.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_15.setBounds(325, 66, 249, 25);
		pnlSlavesInfo.add(lblerror_15);

		pnlRemoveSlave = new JPanel();
		lpFeatures.add(pnlRemoveSlave, "name_205708106849200");
		pnlRemoveSlave.setLayout(null);

		lblNewLabel_1 = new JLabel(
				"\u041B\u043E\u0433\u0438\u043D \u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044F:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(117, 109, 193, 25);
		pnlRemoveSlave.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel(
				"\u0418\u0434\u0435\u043D\u0442\u0438\u0444\u0438\u043A\u0430\u0442\u043E\u0440 \u0437\u0430\u043D\u044F\u0442\u0438\u044F:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(117, 156, 242, 25);
		pnlRemoveSlave.add(lblNewLabel_2);

		fldLogin_1 = new JTextField();
		fldLogin_1.setHorizontalAlignment(SwingConstants.CENTER);
		fldLogin_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldLogin_1.setBounds(349, 109, 236, 25);
		pnlRemoveSlave.add(fldLogin_1);
		fldLogin_1.setColumns(10);

		fldClassID_1 = new JTextField();
		fldClassID_1.setHorizontalAlignment(SwingConstants.CENTER);
		fldClassID_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldClassID_1.setBounds(349, 156, 236, 25);
		pnlRemoveSlave.add(fldClassID_1);
		fldClassID_1.setColumns(10);

		btnRemove_1 = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		btnRemove_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRemove_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// T3_drop_related_student_from_class(IN students_email VARCHAR(45), IN
				// tutors_email VARCHAR(45), IN class_id INT, OUT result_status TINYINT)

				// A14_drop_student_from_class(IN email VARCHAR(45), IN class_id INT, OUT
				// result_status TINYINT)
				String name = fldLogin_1.getText();
				String id = fldClassID_1.getText();
				if (type == 2) {
					String procedure = "T3_drop_related_student_from_class";
					String[] names = { "students_email", "tutors_email", "class_id" };
					String[] types = { "String", "String", "Int" };
					String[] values = { name, login, id };
					ResultSet response = db.request(procedure, names, types, values);
				} else {
					String procedure = "A14_drop_student_from_class";
					String[] names = { "email", "class_id" };
					String[] types = { "String", "Int" };
					String[] values = { name, id };
					ResultSet response = db.request(procedure, names, types, values);
				}
				try {
					int code = db.getStatus();
					if (code == 0) {
						lblerror_2.setText("Совпадений не найдено!");
						lblerror_2.setForeground(Color.RED);
						lblerror_2.setVisible(true);
					} else {
						lblerror_2.setText("Успешно!");
						lblerror_2.setForeground(Color.GREEN);
						lblerror_2.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemove_1.setBounds(620, 131, 126, 25);
		pnlRemoveSlave.add(btnRemove_1);

		lblerror_2 = new JLabel(
				"\u0421\u043E\u0432\u0430\u0434\u0435\u043D\u0438\u0439 \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u043E!");
		lblerror_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_2.setForeground(Color.RED);
		lblerror_2.setBounds(203, 211, 497, 25);
		pnlRemoveSlave.add(lblerror_2);

		pnlSetCalssSlave = new JPanel();
		lpFeatures.add(pnlSetCalssSlave, "name_205851538972100");
		pnlSetCalssSlave.setLayout(null);

		fldLogin_3 = new JTextField();
		fldLogin_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldLogin_3.setHorizontalAlignment(SwingConstants.CENTER);
		fldLogin_3.setBounds(438, 79, 212, 25);
		pnlSetCalssSlave.add(fldLogin_3);
		fldLogin_3.setColumns(10);

		fldClassID_3 = new JTextField();
		fldClassID_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldClassID_3.setHorizontalAlignment(SwingConstants.CENTER);
		fldClassID_3.setBounds(438, 115, 212, 25);
		pnlSetCalssSlave.add(fldClassID_3);
		fldClassID_3.setColumns(10);

		lblNewLabel_4 = new JLabel(
				"\u041B\u043E\u0433\u0438\u043D \u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044F:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(234, 77, 189, 25);
		pnlSetCalssSlave.add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel(
				"\u0418\u0434\u0435\u043D\u0442\u0438\u0444\u0438\u043A\u0430\u0442\u043E\u0440 \u0437\u0430\u043D\u044F\u0442\u0438\u044F:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5.setBounds(234, 113, 229, 25);
		pnlSetCalssSlave.add(lblNewLabel_5);

		JComboBox cbPrior = new JComboBox();
		cbPrior.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbPrior.setModel(new DefaultComboBoxModel(new String[] {
				"\u041F\u0440\u0438\u043E\u0440\u0438\u0442\u0435\u0442", "\u041D\u0438\u0437\u043A\u0438\u0439",
				"\u0412\u044B\u0441\u043E\u043A\u0438\u0439", "\u0421\u0440\u0435\u0434\u043D\u0438\u0439" }));
		cbPrior.setSelectedIndex(0);
		cbPrior.setBounds(438, 153, 212, 25);
		pnlSetCalssSlave.add(cbPrior);

		JLabel lblerror_3 = new JLabel(
				"\u0417\u0430\u043D\u044F\u0442\u0438\u0435 \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u043E");
		lblerror_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_3.setForeground(Color.RED);
		lblerror_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_3.setBounds(316, 205, 249, 25);
		pnlSetCalssSlave.add(lblerror_3);

		JButton btnSet = new JButton("\u041D\u0430\u0437\u043D\u0430\u0447\u0438\u0442\u044C");
		btnSet.setFont(new Font("Verdana", Font.BOLD, 15));
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// T2_set_related_student_to_class(IN students_email VARCHAR(45), IN
				// tutors_email VARCHAR(45), IN class_id INT, IN priority VARCHAR(7), OUT
				// result_status TINYINT)

				// A13_set_student_to_class (IN email VARCHAR(45), IN class_id INT, IN priority
				// VARCHAR(7), OUT result_status TINYINT)
				String name = fldLogin_3.getText();
				String id = fldClassID_3.getText();
				String priority = (String) cbPrior.getSelectedItem();
				if (priority.equals("Приоритет")) {
					lblerror_3.setText("Выберете приоритет!");
					lblerror_3.setForeground(Color.RED);
					lblerror_3.setVisible(true);
				} else {
					if (type == 2) {
						String procedure = "T2_set_related_student_to_class";
						String[] names = { "students_email", "tutors_email", "class_id", "priority" };
						String[] types = { "String", "String", "Int", "String" };
						String[] values = { name, login, id, priority };
						ResultSet response = db.request(procedure, names, types, values);
					} else {
						String procedure = "A13_set_student_to_class";
						String[] names = { "email", "class_id", "priority" };
						String[] types = { "String", "Int", "String" };
						String[] values = { name, id, priority };
						ResultSet response = db.request(procedure, names, types, values);
					}

					try {
						int code = db.getStatus();
						if (code == 0) {
							lblerror_3.setText("Совпадений не найдено!");
							lblerror_3.setForeground(Color.RED);
							lblerror_3.setVisible(true);
						} else {
							lblerror_3.setText("Успешно!");
							lblerror_3.setForeground(Color.GREEN);
							lblerror_3.setVisible(true);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		btnSet.setBounds(234, 153, 150, 25);
		pnlSetCalssSlave.add(btnSet);

		pnlDefault = new JPanel();
		lpFeatures.add(pnlDefault, "name_208114001379900");

		pnlPeopleInfo = new JPanel();
		lpFeatures.add(pnlPeopleInfo, "name_209663407575100");
		pnlPeopleInfo.setLayout(null);

		JScrollPane spPeopleInfo = new JScrollPane();
		spPeopleInfo.setBounds(153, 110, 563, 150);
		pnlPeopleInfo.add(spPeopleInfo);

		cbStudTeach_1 = new JComboBox();
		cbStudTeach_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String state = (String) cbStudTeach_1.getSelectedItem();
				cbSort.removeAllItems();
				cbSort.addItem("ФИО");
				cbSort.addItem("Количество занятий");
				if (state.equals("Преподаватели")) {
					cbSort.addItem("Кличество подопечных");
					cbSort.addItem("Количество кабинетов");
				}
			}
		});
		cbStudTeach_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbStudTeach_1
				.setModel(new DefaultComboBoxModel(new String[] { "\u0421\u0442\u0443\u0434\u0435\u043D\u0442\u044B ",
						"\u041F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u0438" }));
		cbStudTeach_1.setSelectedIndex(0);
		cbStudTeach_1.setBounds(324, 11, 218, 25);
		pnlPeopleInfo.add(cbStudTeach_1);

		cbSort = new JComboBox();
		cbSort.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbSort.setModel(new DefaultComboBoxModel(new String[] { "\u0424\u0418\u041E",
				"\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u0437\u0430\u043D\u044F\u0442\u0438\u0439" }));
		cbSort.setSelectedIndex(0);
		cbSort.setBounds(324, 47, 218, 25);
		pnlPeopleInfo.add(cbSort);

		JLabel lblNewLabel_22 = new JLabel("\u0418\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F \u043E:");
		lblNewLabel_22.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_22.setBounds(161, 11, 150, 25);
		pnlPeopleInfo.add(lblNewLabel_22);

		JLabel lblNewLabel_23 = new JLabel(
				"\u0421\u043E\u0440\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u043F\u043E:");
		lblNewLabel_23.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_23.setBounds(161, 47, 142, 25);
		pnlPeopleInfo.add(lblNewLabel_23);

		JButton btnShow_20 = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C");
		btnShow_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO положение таблицы
				// A1_tutors_info (IN sort_type TINYINT, OUT result_status TINYINT)
				// A2_students_info (IN sort_type TINYINT, OUT result_status TINYINT)
				String who = (String) cbStudTeach_1.getSelectedItem();
				String sort = cbSort.getSelectedIndex() + "";
				String procedure;
				if (who.equals("Студенты"))
					procedure = "A2_students_info";
				else
					procedure = "A1_tutors_info";
				String[] names = { "sort_type" };
				String[] types = { "Int" };
				String[] values = { sort };

				try {
					ResultSet response = db.request(procedure, names, types, values);
					JTable table = fill_table(response);
					table.setEnabled(false);
					spPeopleInfo.setViewportView(table);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnShow_20.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnShow_20.setBounds(587, 28, 129, 25);
		pnlPeopleInfo.add(btnShow_20);

		pnlAddClass = new JPanel();
		lpFeatures.add(pnlAddClass, "name_210257722133500");
		pnlAddClass.setLayout(null);

		fldClassName = new JTextField();
		fldClassName.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldClassName.setHorizontalAlignment(SwingConstants.CENTER);
		fldClassName.setBounds(354, 110, 240, 25);
		pnlAddClass.add(fldClassName);
		fldClassName.setColumns(10);

		fldClass = new JTextField();
		fldClass.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldClass.setHorizontalAlignment(SwingConstants.CENTER);
		fldClass.setBounds(355, 178, 148, 25);
		pnlAddClass.add(fldClass);
		fldClass.setColumns(10);

		cbBuilding_1 = new JComboBox();
		cbBuilding_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbBuilding_1.setModel(
				new DefaultComboBoxModel(new String[] { "\u041A\u043E\u0440\u043F\u0443\u0441", "\u0410", "\u0411" }));
		cbBuilding_1.setSelectedIndex(0);
		cbBuilding_1.setBounds(513, 178, 82, 25);
		pnlAddClass.add(cbBuilding_1);

		fldDate = new JTextField();
		fldDate.setForeground(Color.LIGHT_GRAY);
		fldDate.setText("dd.mm.yyyy");
		fldDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldDate.setHorizontalAlignment(SwingConstants.CENTER);
		fldDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (fldDate.getText().equals("dd.mm.yyyy")) {
					fldDate.setText("");
					fldDate.setForeground(Color.BLACK);
				}
			}
		});
		fldDate.setBounds(354, 40, 240, 25);
		pnlAddClass.add(fldDate);

		fldTime = new JTextField();
		fldTime.setForeground(Color.LIGHT_GRAY);
		fldTime.setText("hh:mm");
		fldTime.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldTime.setHorizontalAlignment(SwingConstants.CENTER);
		fldTime.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (fldTime.getText().equals("hh:mm")) {
					fldTime.setText("");
					fldTime.setForeground(Color.BLACK);
				}
			}
		});
		fldTime.setBounds(354, 75, 240, 25);
		pnlAddClass.add(fldTime);

		JLabel lblNewLabel_9 = new JLabel("\u0414\u0430\u0442\u0430:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_9.setBounds(161, 40, 89, 25);
		pnlAddClass.add(lblNewLabel_9);

		JLabel lblNewLabel_15 = new JLabel("\u0412\u0440\u0435\u043C\u044F:");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_15.setBounds(161, 75, 125, 25);
		pnlAddClass.add(lblNewLabel_15);

		JLabel lblNewLabel_16 = new JLabel(
				"\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0434\u0438\u0441\u0446\u0438\u043F\u043B\u0438\u043D\u044B:");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_16.setBounds(161, 110, 193, 25);
		pnlAddClass.add(lblNewLabel_16);

		JLabel lblNewLabel_17 = new JLabel(
				"\u041D\u043E\u043C\u0435\u0440 \u043A\u0430\u0431\u0438\u043D\u0435\u0442\u0430:");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_17.setBounds(162, 180, 143, 25);
		pnlAddClass.add(lblNewLabel_17);

		lblerror_16 = new JLabel(
				"\u0417\u0430\u043D\u044F\u0442\u0438\u0435 \u0441\u043E\u0437\u0434\u0430\u043D\u043E!");
		lblerror_16.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_16.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_16.setBounds(322, 214, 301, 25);
		pnlAddClass.add(lblerror_16);

		JButton btnCreate = new JButton("\u0421\u043E\u0437\u0434\u0430\u0442\u044C");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO разобраться с вводом параметров
				// A3_set_class(IN date_ VARCHAR(10), IN time_ VARCHAR(5), IN class_name
				// VARCHAR(45), IN room_building VARCHAR(1), IN room_number VARCHAR(5), IN email
				// VARCHAR(45), OUT result_status TINYINT)
				String date = fldDate.getText();
				String time = fldTime.getText();
				String title = fldClassName.getText();
				String cabinet = fldClass.getText();
				String teacher = fldTeacher_1.getText();
				String corpus = (String) cbBuilding_1.getSelectedItem();
				if (corpus.equals("Корпус")) {
					lblerror_16.setText("Выберите корпус!");
					lblerror_16.setForeground(Color.RED);
					lblerror_16.setVisible(true);
				} else {
					String procedure = "A3_set_class";
					String[] names = { "date_", "time_", "class_name", "room_building", "room_number", "email" };
					String[] types = { "String", "String", "String", "Int", "String", "String" };
					String[] values = { date, time, title, corpus, cabinet, teacher };
					try {
						ResultSet response = db.request(procedure, names, types, values);
						int code = db.getStatus();
						if (code == 0) {
							lblerror_16.setText("Не удалось создать занятие!");
							lblerror_16.setForeground(Color.RED);
							lblerror_16.setVisible(true);
						} else {
							lblerror_16.setText("Занятие создано!");
							lblerror_16.setForeground(Color.GREEN);
							lblerror_16.setVisible(true);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCreate.setBounds(645, 110, 125, 25);
		pnlAddClass.add(btnCreate);

		lblNewLabel_18 = new JLabel("\u041F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044C:");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_18.setBounds(161, 145, 167, 25);
		pnlAddClass.add(lblNewLabel_18);

		fldTeacher_1 = new JTextField();
		fldTeacher_1.setHorizontalAlignment(SwingConstants.CENTER);
		fldTeacher_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldTeacher_1.setBounds(354, 145, 240, 25);
		pnlAddClass.add(fldTeacher_1);
		fldTeacher_1.setColumns(10);

		pnlRemoveClass = new JPanel();
		lpFeatures.add(pnlRemoveClass, "name_211059070919100");
		pnlRemoveClass.setLayout(null);

		fldClassID = new JTextField();
		fldClassID.setHorizontalAlignment(SwingConstants.CENTER);
		fldClassID.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldClassID.setBounds(332, 130, 228, 25);
		pnlRemoveClass.add(fldClassID);
		fldClassID.setColumns(10);

		lblerror_5 = new JLabel(
				"\u0417\u0430\u043D\u044F\u0442\u0438\u0435 \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D\u043E!");
		lblerror_5.setForeground(Color.RED);
		lblerror_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_5.setBounds(303, 183, 296, 25);
		pnlRemoveClass.add(lblerror_5);

		JButton btnDeleteClass = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		btnDeleteClass.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDeleteClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A4_drop_class(IN class_id INT, OUT result_status TINYINT)
				String id = fldClassID.getText();
				String procedure = "A4_drop_class";
				String[] names = { "class_id" };
				String[] types = { "Int" };
				String[] values = { id };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (code == 0) {
						lblerror_5.setText("Занятие не найдено!");
						lblerror_5.setForeground(Color.RED);
						lblerror_5.setVisible(true);
					} else {
						lblerror_5.setText("Занятие удалено!");
						lblerror_5.setForeground(Color.GREEN);
						lblerror_5.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnDeleteClass.setBounds(592, 130, 135, 25);
		pnlRemoveClass.add(btnDeleteClass);

		JLabel lblNewLabel_3 = new JLabel(
				"\u0418\u0434\u0435\u043D\u0442\u0438\u0444\u0438\u043A\u0430\u0442\u043E\u0440 \u0437\u0430\u043D\u044F\u0442\u0438\u044F:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(114, 130, 228, 25);
		pnlRemoveClass.add(lblNewLabel_3);

		pnlSetTeacher = new JPanel();
		lpFeatures.add(pnlSetTeacher, "name_211280351764400");
		pnlSetTeacher.setLayout(null);

		fldEmail = new JTextField();
		fldEmail.setHorizontalAlignment(SwingConstants.CENTER);
		fldEmail.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldEmail.setBounds(352, 91, 216, 25);
		pnlSetTeacher.add(fldEmail);
		fldEmail.setColumns(10);

		fldClassID_2 = new JTextField();
		fldClassID_2.setHorizontalAlignment(SwingConstants.CENTER);
		fldClassID_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldClassID_2.setBounds(352, 140, 216, 25);
		pnlSetTeacher.add(fldClassID_2);
		fldClassID_2.setColumns(10);

		JComboBox cbTeacherStatuse = new JComboBox();
		cbTeacherStatuse.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbTeacherStatuse.setModel(new DefaultComboBoxModel(new String[] { "\u0421\u0442\u0430\u0442\u0443\u0441",
				"\u041E\u0441\u043D\u043E\u0432\u043D\u043E\u0439",
				"\u0410\u0441\u0441\u0438\u0441\u0442\u0435\u043D\u0442",
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C" }));
		cbTeacherStatuse.setSelectedIndex(0);
		cbTeacherStatuse.setBounds(590, 89, 137, 25);
		pnlSetTeacher.add(cbTeacherStatuse);

		JButton btnSetTeacher_2 = new JButton("\u041F\u0440\u0438\u043C\u0435\u043D\u0438\u0442\u044C");
		btnSetTeacher_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSetTeacher_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// A5_set_tutor_to_class(IN email VARCHAR(45), IN class_id INT, IN status_
				// VARCHAR(9), OUT result_status TINYINT)
				// A6_drop_tutor_from_class(IN email VARCHAR(45), IN class_id INT, OUT
				// result_status TINYINT)
				String name = fldEmail.getText();
				String id = fldClassID_2.getText();
				String status = (String) cbTeacherStatuse.getSelectedItem();
				ResultSet response;
				if (status == "Статус") {
					lblerror_8.setText("Выберете статус!");
					lblerror_8.setForeground(Color.RED);
					lblerror_8.setVisible(true);
				} else if (status == "Удалить") {
					String procedure = "A6_drop_tutor_from_class";
					String[] names = { "email,class_id" };
					String[] types = { "String,Int" };
					String[] values = { name, id };
					response = db.request(procedure, names, types, values);
				} else {
					String procedure = "A5_set_tutor_to_class";
					String[] names = { "email,class_id,status_" };
					String[] types = { "String,Int,String" };
					String[] values = { name, id, status };
					response = db.request(procedure, names, types, values);
				}
				try {
					int code = db.getStatus();
					if (code == 0) {
						lblerror_8.setText("Что-то пошло не так!");
						lblerror_8.setForeground(Color.RED);
						lblerror_8.setVisible(true);
					} else {
						lblerror_8.setText("Успешно!");
						lblerror_8.setForeground(Color.GREEN);
						lblerror_8.setVisible(true);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSetTeacher_2.setBounds(590, 140, 137, 25);
		pnlSetTeacher.add(btnSetTeacher_2);

		lblerror_8 = new JLabel(
				"\u041F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044C \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D!");
		lblerror_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_8.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_8.setBounds(296, 189, 342, 25);
		pnlSetTeacher.add(lblerror_8);

		lblNewLabel_12 = new JLabel(
				"\u041B\u043E\u0433\u0438\u043D \u043F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044F:");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_12.setBounds(132, 91, 200, 25);
		pnlSetTeacher.add(lblNewLabel_12);

		lblNewLabel_13 = new JLabel(
				"\u0418\u0434\u0435\u043D\u0442\u0438\u0444\u0438\u043A\u0430\u0442\u043E\u0440 \u0437\u0430\u043D\u044F\u0442\u0438\u044F:");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_13.setBounds(135, 140, 250, 25);
		pnlSetTeacher.add(lblNewLabel_13);

		pnlRemoveMember = new JPanel();
		lpFeatures.add(pnlRemoveMember, "name_211654409985600");
		pnlRemoveMember.setLayout(null);

		fldStudent = new JTextField();
		fldStudent.setHorizontalAlignment(SwingConstants.CENTER);
		fldStudent.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldStudent.setBounds(239, 78, 228, 25);
		pnlRemoveMember.add(fldStudent);
		fldStudent.setColumns(10);

		fldTeacher = new JTextField();
		fldTeacher.setHorizontalAlignment(SwingConstants.CENTER);
		fldTeacher.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldTeacher.setBounds(239, 120, 228, 25);
		pnlRemoveMember.add(fldTeacher);
		fldTeacher.setColumns(10);

		fldCabinet = new JTextField();
		fldCabinet.setHorizontalAlignment(SwingConstants.CENTER);
		fldCabinet.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldCabinet.setBounds(239, 167, 118, 25);
		pnlRemoveMember.add(fldCabinet);
		fldCabinet.setColumns(10);

		JComboBox cbCorpus = new JComboBox();
		cbCorpus.setModel(
				new DefaultComboBoxModel(new String[] { "\u041A\u043E\u0440\u043F\u0443\u0441", "\u0410", "\u0411" }));
		cbCorpus.setSelectedIndex(0);
		cbCorpus.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbCorpus.setBounds(368, 167, 99, 25);
		pnlRemoveMember.add(cbCorpus);

		JButton btnRemoveStudent_2 = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		btnRemoveStudent_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRemoveStudent_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A10_drop_student(IN email VARCHAR(45), OUT result_status TINYINT)
				String name = fldStudent.getText();
				String procedure = "A10_drop_student";
				String[] names = { "email" };
				String[] types = { "String" };
				String[] values = { name };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (code == 0) {
						lblerror_21.setText("Студент не найден!");
						lblerror_21.setForeground(Color.RED);
						lblerror_21.setVisible(true);
					} else {
						lblerror_21.setText("Студент удален!");
						lblerror_21.setForeground(Color.GREEN);
						lblerror_21.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveStudent_2.setBounds(479, 77, 109, 25);
		pnlRemoveMember.add(btnRemoveStudent_2);

		JButton btnRemoveTeacher = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		btnRemoveTeacher.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRemoveTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A8_drop_tutor(IN email VARCHAR(45), IN self_email VARCHAR(45), OUT
				// result_status TINYINT)
				String name = fldTeacher.getText();
				String procedure = "A8_drop_tutor";
				String[] names = { "email", "self_email" };
				String[] types = { "String", "String" };
				String[] values = { name };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (code == 0) {
						lblerror_22.setText("Преподаватель не найден!");
						lblerror_22.setForeground(Color.RED);
						lblerror_22.setVisible(true);
					} else {
						lblerror_22.setText("Преподаватель удален!");
						lblerror_22.setForeground(Color.GREEN);
						lblerror_22.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveTeacher.setBounds(479, 119, 109, 25);
		pnlRemoveMember.add(btnRemoveTeacher);

		JButton btnRemoveCabinet = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		btnRemoveCabinet.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnRemoveCabinet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A12_drop_room (IN room_building VARCHAR(1), IN room_number VARCHAR(5), OUT
				// result_status TINYINT)
				String cabinet = fldCabinet.getText();
				String corpus = (String) cbCorpus.getSelectedItem();
				if (corpus.equals("Корпус")) {
					lblerror_23.setText("Выберете корпус!");
					lblerror_23.setForeground(Color.RED);
					lblerror_23.setVisible(true);
				} else {
					String procedure = "A12_drop_room";
					String[] names = { "room_building", "room_number" };
					String[] types = { "String", "String" };
					String[] values = { corpus, cabinet };
					try {
						ResultSet response = db.request(procedure, names, types, values);
						int code = db.getStatus();
						if (code == 0) {
							lblerror_23.setText("Аудитория не найдена!");
							lblerror_23.setForeground(Color.RED);
							lblerror_23.setVisible(true);
						} else {
							lblerror_23.setText("Аудитория удалена!");
							lblerror_23.setForeground(Color.GREEN);
							lblerror_23.setVisible(true);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnRemoveCabinet.setBounds(479, 167, 109, 25);
		pnlRemoveMember.add(btnRemoveCabinet);

		lblerror_23 = new JLabel("\u041A\u0430\u0431\u0438\u043D\u0435\u0442 \u0443\u0434\u0430\u043B\u0435\u043D!");
		lblerror_23.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_23.setBounds(617, 167, 195, 25);
		pnlRemoveMember.add(lblerror_23);

		JLabel lblNewLabel_19 = new JLabel(
				"\u041B\u043E\u0433\u0438\u043D \u0441\u0442\u0443\u0434\u0435\u043D\u0442\u0430:");
		lblNewLabel_19.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_19.setBounds(48, 77, 181, 25);
		pnlRemoveMember.add(lblNewLabel_19);

		JLabel lblNewLabel_20 = new JLabel(
				"\u041B\u043E\u0433\u0438\u043D \u043F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044F:");
		lblNewLabel_20.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_20.setBounds(48, 119, 181, 25);
		pnlRemoveMember.add(lblNewLabel_20);

		JLabel lblNewLabel_21 = new JLabel(
				"\u041D\u043E\u043C\u0435\u0440 \u043A\u0430\u0431\u0438\u043D\u0435\u0442\u0430:");
		lblNewLabel_21.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_21.setBounds(48, 167, 181, 25);
		pnlRemoveMember.add(lblNewLabel_21);

		lblerror_22 = new JLabel(
				"\u041F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044C \u0443\u0434\u0430\u043B\u0435\u043D!");
		lblerror_22.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_22.setBounds(617, 120, 228, 25);
		pnlRemoveMember.add(lblerror_22);

		lblerror_21 = new JLabel("\u0421\u0442\u0443\u0434\u0435\u043D\u0442 \u0443\u0434\u0430\u043B\u0435\u043D!");
		lblerror_21.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_21.setBounds(617, 78, 195, 25);
		pnlRemoveMember.add(lblerror_21);

		JPanel pnlStudentProfile = new JPanel();
		lpFeatures.add(pnlStudentProfile, "name_269667741094100");
		pnlStudentProfile.setLayout(null);

		fldPassport = new JTextField();
		fldPassport.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fldPassport.setText("");
				fldPassport.setForeground(Color.BLACK);
			}
		});
		fldPassport.setHorizontalAlignment(SwingConstants.CENTER);
		fldPassport.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldPassport.setBounds(421, 85, 245, 25);
		pnlStudentProfile.add(fldPassport);
		fldPassport.setColumns(10);

		JButton btnUpdate = new JButton(
				"\u041E\u0431\u043D\u043E\u0432\u0438\u0442\u044C \u0434\u0430\u043D\u043D\u044B\u0435");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A7_set_tutor(IN email VARCHAR(45), IN pasport_series VARCHAR(4), IN
				// pasport_number VARCHAR(6), IN name_ VARCHAR(45), IN surname_ VARCHAR(45), IN
				// last_name VARCHAR(45), IN supervising_flag TINYINT, OUT result_status
				// TINYINT)
				// A9_set_student (IN email VARCHAR(45), IN pasport_series VARCHAR(4), IN
				// pasport_number VARCHAR(6), IN name_ VARCHAR(45), IN surname_ VARCHAR(45), IN
				// last_name VARCHAR(45), IN superviser_email VARCHAR(45), OUT result_status
				// TINYINT)
				String who = (String) cbSelectTS.getSelectedItem();
				String mail = fldMial.getText();
				String[] pasport = fldPassport.getText().split(" ");
				String[] fio = fldFIO.getText().split(" ");
				String mail_tutor = fldMail_Tutor.getText();
				boolean superviser = cbSupervisor.isSelected();
				if (who.equals("Студент")) {
					String procedure = "A9_set_student";
					String[] names = { "email", "pasport_series", "pasport_number", "name_", "surname_", "last_name",
							"superviser_email" };
					String[] types = { "String", "String", "String", "String", "String", "String", "String" };
					String[] values = { mail, pasport[0], pasport[1], fio[1], fio[0], fio[2], mail_tutor };
					ResultSet response = db.request(procedure, names, types, values);
				} else {
					String procedure = "A7_set_tutor";
					String[] names = { "email", "pasport_series", "pasport_number", "name_", "surname_", "last_name",
							"superviser_email" };
					String[] types = { "String", "String", "String", "String", "String", "String", "Int" };
					String[] values = { mail, pasport[0], pasport[1], fio[1], fio[0], fio[2], superviser ? "1" : "0" };
					ResultSet response = db.request(procedure, names, types, values);
				}

				try {
					int code = db.getStatus();
					if (code == 0) {
						lblerror_40.setText("Ошибка!");
						lblerror_40.setForeground(Color.RED);
						lblerror_40.setVisible(true);
					} else {
						lblerror_40.setText("Данные обновлены!");
						lblerror_40.setForeground(Color.GREEN);
						lblerror_40.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(321, 217, 208, 25);
		pnlStudentProfile.add(btnUpdate);

		cbSelectTS = new JComboBox();
		cbSelectTS.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selected = (String) cbSelectTS.getSelectedItem();
				if (selected.equals("Студент")) {
					cbSupervisor.setEnabled(false);
					fldMail_Tutor.setEnabled(true);
				} else {
					cbSupervisor.setEnabled(true);
					fldMail_Tutor.setEnabled(false);
				}
			}
		});
		cbSelectTS.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbSelectTS.setModel(new DefaultComboBoxModel(new String[] { "\u0421\u0442\u0443\u0434\u0435\u043D\u0442",
				"\u041F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044C" }));
		cbSelectTS.setSelectedIndex(0);
		cbSelectTS.setBounds(329, 8, 175, 22);
		pnlStudentProfile.add(cbSelectTS);

		fldMial = new JTextField();
		fldMial.setHorizontalAlignment(SwingConstants.CENTER);
		fldMial.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldMial.setBounds(421, 49, 245, 25);
		pnlStudentProfile.add(fldMial);
		fldMial.setColumns(10);

		fldFIO = new JTextField();
		fldFIO.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				fldFIO.setText("");
				fldFIO.setForeground(Color.BLACK);
			}
		});
		fldFIO.setHorizontalAlignment(SwingConstants.CENTER);
		fldFIO.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldFIO.setBounds(421, 121, 245, 25);
		pnlStudentProfile.add(fldFIO);
		fldFIO.setColumns(10);

		fldMail_Tutor = new JTextField();
		fldMail_Tutor.setHorizontalAlignment(SwingConstants.CENTER);
		fldMail_Tutor.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldMail_Tutor.setBounds(421, 157, 245, 25);
		pnlStudentProfile.add(fldMail_Tutor);
		fldMail_Tutor.setColumns(10);

		cbSupervisor = new JCheckBox("\u0421\u0443\u043F\u0435\u0440\u0432\u0430\u0439\u0437\u0435\u0440");
		cbSupervisor.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbSupervisor.setBounds(526, 7, 140, 25);
		pnlStudentProfile.add(cbSupervisor);

		JLabel lblNewLabel_24 = new JLabel("\u041F\u043E\u0447\u0442\u0430:");
		lblNewLabel_24.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_24.setBounds(219, 49, 145, 25);
		pnlStudentProfile.add(lblNewLabel_24);

		JLabel lblNewLabel_25 = new JLabel(
				"\u041F\u0430\u0441\u043F\u043E\u0440\u0442\u043D\u044B\u0435 \u0434\u0430\u043D\u043D\u044B\u0435:");
		lblNewLabel_25.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_25.setBounds(219, 85, 192, 25);
		pnlStudentProfile.add(lblNewLabel_25);

		JLabel lblNewLabel_26 = new JLabel("\u0424\u0418\u041E:");
		lblNewLabel_26.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_26.setBounds(219, 117, 49, 25);
		pnlStudentProfile.add(lblNewLabel_26);

		JLabel lblNewLabel_27 = new JLabel(
				"\u041F\u043E\u0447\u0442\u0430 \u0442\u044C\u044E\u0442\u043E\u0440\u0430:");
		lblNewLabel_27.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_27.setBounds(219, 153, 192, 25);
		pnlStudentProfile.add(lblNewLabel_27);

		lblerror_40 = new JLabel(
				"\u0414\u0430\u043D\u043D\u044B\u0435 \u043E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u044B!");
		lblerror_40.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_40.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_40.setBounds(311, 189, 223, 25);
		pnlStudentProfile.add(lblerror_40);

		JPanel pnlCreateCabinet = new JPanel();
		lpFeatures.add(pnlCreateCabinet, "name_269678159310100");
		pnlCreateCabinet.setLayout(null);

		fldCabinet_9 = new JTextField();
		fldCabinet_9.setHorizontalAlignment(SwingConstants.CENTER);
		fldCabinet_9.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldCabinet_9.setBounds(352, 79, 185, 25);
		pnlCreateCabinet.add(fldCabinet_9);
		fldCabinet_9.setColumns(10);

		JComboBox cbBuilding_9 = new JComboBox();
		cbBuilding_9.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbBuilding_9.setModel(
				new DefaultComboBoxModel(new String[] { "\u041A\u043E\u0440\u043F\u0443\u0441", "\u0410", "\u0411" }));
		cbBuilding_9.setSelectedIndex(0);
		cbBuilding_9.setBounds(565, 79, 109, 25);
		pnlCreateCabinet.add(cbBuilding_9);

		JLabel lblerror_9 = new JLabel(
				"\u041A\u0430\u0431\u0438\u043D\u0435\u0442 \u0441\u043E\u0437\u0434\u0430\u043D!");
		lblerror_9.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror_9.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblerror_9.setBounds(297, 172, 303, 25);
		pnlCreateCabinet.add(lblerror_9);

		JButton btnCreate_9 = new JButton("\u0421\u043E\u0437\u0434\u0430\u0442\u044C");
		btnCreate_9.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCreate_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A11_set_room (IN room_building VARCHAR(1), IN room_number VARCHAR(5), IN
				// email VARCHAR(45), OUT result_status TINYINT)
				String id = fldCabinet_9.getText();
				String corpus = (String) cbBuilding_9.getSelectedItem();
				String name = fldLogin_9.getText();
				String procedure = "A11_set_room";
				String[] names = { "room_building", "room_number", "email" };
				String[] types = { "String", "String", "String" };
				String[] values = { corpus, id, name };
				if (corpus.equals("Корпус")) {
					lblerror_9.setText("Выберете корпус!");
					lblerror_9.setForeground(Color.RED);
					lblerror_9.setVisible(true);
				} else {
					ResultSet response = db.request(procedure, names, types, values);
					try {
						int code = db.getStatus();
						if (code == 0) {
							lblerror_9.setText("Преподаватель не найден!");
							lblerror_9.setForeground(Color.RED);
							lblerror_9.setVisible(true);
						} else {
							lblerror_9.setText("Иноформация обновлена!");
							lblerror_9.setForeground(Color.GREEN);
							lblerror_9.setVisible(true);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnCreate_9.setBounds(565, 122, 109, 25);
		pnlCreateCabinet.add(btnCreate_9);

		lblNewLabel_10 = new JLabel("\u041D\u043E\u043C\u0435\u0440 \u043A\u0430\u0431\u0438\u043D\u0435\u0442\u0430:");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_10.setBounds(68, 79, 218, 25);
		pnlCreateCabinet.add(lblNewLabel_10);

		fldLogin_9 = new JTextField();
		fldLogin_9.setHorizontalAlignment(SwingConstants.CENTER);
		fldLogin_9.setFont(new Font("Tahoma", Font.BOLD, 15));
		fldLogin_9.setBounds(352, 122, 185, 25);
		pnlCreateCabinet.add(fldLogin_9);
		fldLogin_9.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel(
				"\u041E\u0442\u0432\u0435\u0442\u0441\u0442\u0432\u0435\u043D\u043D\u044B\u0439 \u043F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044C:");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_11.setBounds(68, 122, 274, 25);
		pnlCreateCabinet.add(lblNewLabel_11);

		JPanel pnlPasswords = new JPanel();
		lpFeatures.add(pnlPasswords, "name_176560315195500");
		pnlPasswords.setLayout(null);

		JScrollPane spPasswords = new JScrollPane();
		spPasswords.setBounds(82, 30, 622, 212);
		pnlPasswords.add(spPasswords);

		pnlFeatureButtons = new JPanel();
		pnlFeatureButtons.setForeground(Color.WHITE);
		pnlFeatureButtons.setLayout(null);
		pnlFeatureButtons.setBounds(0, 0, 886, 241);
		pnlWorkingArea.add(pnlFeatureButtons);

		btnTimetable = new JButton("\u041C\u043E\u0451 \u0440\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u0435");
		btnTimetable.setForeground(Color.GREEN);
		btnTimetable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO если нет занятий + положение таблицы
				// U1_get_personal_timetable(IN email CHAR(45))
				String procedure = "U1_get_personal_timetable";
				String[] names = { "email" };
				String[] types = { "String" };
				String[] values = { login };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (response == null) {
						lblNO.setVisible(true);
						spTimetable.setVisible(false);
					} else {
						JTable table = fill_table(response);
						table.setEnabled(false);
						spTimetable.setForeground(Color.GREEN);
						spTimetable.setViewportView(table);
						lblNO.setVisible(false);
						spTimetable.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				switchPanels(lpFeatures, pnlTimetable);
			}
		});
		btnTimetable.setBounds(121, 38, 89, 23);
		pnlFeatureButtons.add(btnTimetable);

		btnConflicts = new JButton(
				"\u041A\u043E\u043D\u0444\u043B\u0438\u043A\u0442\u044B \u0432 \u0440\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u0438");
		btnConflicts.setForeground(Color.YELLOW);
		btnConflicts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO А если нет занятий? + положение таблицы
				// U5_get_conflict_classes(IN email VARCHAR(45))
				String procedure = "U5_get_conflict_classes";
				String[] names = { "email" };
				String[] types = { "String" };
				String[] values = { login };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					int code = db.getStatus();
					if (code == 0) {
						System.out.print(0);
						spConflicts.setVisible(false);
					} else {
						JTable table = fill_table(response);
						table.setEnabled(false);
						spConflicts.setViewportView(table);
						spConflicts.setVisible(true);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				switchPanels(lpFeatures, pnlConflicts);
			}
		});
		btnConflicts.setBounds(215, 38, 89, 23);
		pnlFeatureButtons.add(btnConflicts);

		btnStudTeachTimetable = new JButton(
				"\u0420\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u0435 \u0441\u0442\u0443\u0434\u0435\u043D\u0442/\u043F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044C");
		btnStudTeachTimetable.setForeground(Color.GREEN);
		btnStudTeachTimetable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInput_error.setVisible(false);
				switchPanels(lpFeatures, pnlStudTeachTimetable);
			}
		});
		btnStudTeachTimetable.setBounds(215, 72, 89, 23);
		pnlFeatureButtons.add(btnStudTeachTimetable);

		btnProfile = new JButton("\u041B\u0438\u0447\u043D\u044B\u0435 \u0434\u0430\u043D\u043D\u044B\u0435");
		btnProfile.setForeground(Color.GREEN);
		btnProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO положение таблицы
				// U6_get_exact_info(IN email VARCHAR(45))
				String procedure = "U6_get_exact_info";
				String[] names = { "email" };
				String[] types = { "String" };
				String[] values = { login };
				try {
					ResultSet response = db.request(procedure, names, types, values);
					JTable table = fill_profile(response);
					table.setEnabled(false);
					spProfile.setViewportView(table);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				switchPanels(lpFeatures, pnlProfile);
			}
		});
		btnProfile.setBounds(314, 38, 89, 23);
		pnlFeatureButtons.add(btnProfile);

		btnClassesList = new JButton("\u0421\u043F\u0438\u0441\u043E\u043A \u0437\u0430\u043D\u044F\u0442\u0438\u0439");
		btnClassesList.setForeground(Color.GREEN);
		btnClassesList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO таблица
				// U3_get_classes_info()
				String procedure = "U3_get_classes_info";
				try {
					ResultSet response = db.request(procedure, null, null, null);
					JTable table = fill_table(response);
					table.setEnabled(false);
					spClassList.setViewportView(table);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				switchPanels(lpFeatures, pnlClassList);
			}
		});
		btnClassesList.setBounds(314, 72, 89, 23);
		pnlFeatureButtons.add(btnClassesList);

		btnClassTimetable = new JButton(
				"\u0420\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u0435 \u043A\u0430\u0431\u0438\u043D\u0435\u0442");
		btnClassTimetable.setForeground(Color.GREEN);
		btnClassTimetable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror.setVisible(false);
				switchPanels(lpFeatures, pnlClassTimetable);
			}
		});
		btnClassTimetable.setBounds(121, 72, 89, 23);
		pnlFeatureButtons.add(btnClassTimetable);

		btnStudentList = new JButton(
				"\u0421\u043F\u0438\u0441\u043E\u043A \u0441\u0442\u0443\u0434\u0435\u043D\u0442\u043E\u0432 \u043D\u0430 \u0437\u0430\u043D\u044F\u0442\u0438\u0438");
		btnStudentList.setForeground(Color.GREEN);
		btnStudentList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_1.setVisible(false);
				switchPanels(lpFeatures, pnlStudentList);
			}
		});
		btnStudentList.setBounds(413, 38, 89, 23);
		pnlFeatureButtons.add(btnStudentList);

		btnSlaveInfo = new JButton(
				"\u0418\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F \u043E \u043F\u043E\u0434\u043E\u043F\u0435\u0447\u043D\u043E\u043C");
		btnSlaveInfo.setForeground(Color.YELLOW);
		btnSlaveInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_15.setVisible(false);
				switchPanels(lpFeatures, pnlSlavesInfo);
			}
		});
		btnSlaveInfo.setBounds(121, 112, 89, 23);
		pnlFeatureButtons.add(btnSlaveInfo);

		btnRemoveSlave = new JButton(
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u043F\u043E\u0434\u043E\u043F\u0435\u0447\u043D\u043E\u0433\u043E \u0441 \u0437\u0430\u043D\u044F\u0442\u0438\u044F");
		btnRemoveSlave.setForeground(Color.GREEN);
		btnRemoveSlave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_2.setVisible(false);
				switchPanels(lpFeatures, pnlRemoveSlave);
			}
		});
		btnRemoveSlave.setBounds(215, 112, 89, 23);
		pnlFeatureButtons.add(btnRemoveSlave);

		btnSetClassSlave = new JButton(
				"\u041D\u0430\u0437\u043D\u0430\u0447\u0438\u0442\u044C \u0437\u0430\u043D\u044F\u0442\u0438\u0435 \u043F\u043E\u0434\u043E\u043F\u0435\u0447\u043D\u043E\u043C\u0443");
		btnSetClassSlave.setForeground(Color.GREEN);
		btnSetClassSlave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_3.setVisible(false);
				switchPanels(lpFeatures, pnlSetCalssSlave);
			}
		});
		btnSetClassSlave.setBounds(314, 112, 89, 23);
		pnlFeatureButtons.add(btnSetClassSlave);

		btnPeopleInfo = new JButton(
				"\u0418\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F \u043E \u0441\u043E\u0442\u0440\u0443\u0434\u043D\u0438\u043A\u0430\u0445");
		btnPeopleInfo.setForeground(Color.YELLOW);
		btnPeopleInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// A1_tutors_info (IN sort_type TINYINT)
				switchPanels(lpFeatures, pnlPeopleInfo);
			}
		});
		btnPeopleInfo.setBounds(413, 112, 94, 23);
		pnlFeatureButtons.add(btnPeopleInfo);

		btnAddClass = new JButton(
				"\u0421\u043E\u0437\u0434\u0430\u0442\u044C \u0437\u0430\u043D\u044F\u0442\u0438\u0435");
		btnAddClass.setForeground(Color.YELLOW);
		btnAddClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_16.setVisible(false);
				fldDate.setText("dd.mm.yyyy");
				fldTime.setText("hh:mm");
				fldDate.setForeground(Color.LIGHT_GRAY);
				fldTime.setForeground(Color.LIGHT_GRAY);
				switchPanels(lpFeatures, pnlAddClass);
			}
		});
		btnAddClass.setBounds(121, 158, 89, 23);
		pnlFeatureButtons.add(btnAddClass);

		btnRemoveClass = new JButton(
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0437\u0430\u043D\u044F\u0442\u0438\u0435");
		btnRemoveClass.setForeground(Color.GREEN);
		btnRemoveClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_5.setVisible(false);
				switchPanels(lpFeatures, pnlRemoveClass);
			}
		});
		btnRemoveClass.setBounds(215, 158, 89, 23);
		pnlFeatureButtons.add(btnRemoveClass);

		btnSetTeacher = new JButton(
				"\u041D\u0430\u0437\u043D\u0430\u0447\u0438\u0442\u044C/\u0443\u0434\u0430\u043B\u0438\u0442\u044C \u043F\u0440\u0435\u043F\u043E\u0434\u0430\u0432\u0430\u0442\u0435\u043B\u044F \u043D\u0430 \u0437\u0430\u043D\u044F\u0442\u0438\u0435");
		btnSetTeacher.setForeground(Color.GREEN);
		btnSetTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_8.setVisible(false);
				switchPanels(lpFeatures, pnlSetTeacher);
			}
		});
		btnSetTeacher.setBounds(314, 158, 89, 23);
		pnlFeatureButtons.add(btnSetTeacher);

		btnRemoveMember = new JButton(
				"\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u043A\u043E\u043C\u043F\u043E\u043D\u0435\u043D\u0442");
		btnRemoveMember.setForeground(Color.GREEN);
		btnRemoveMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_21.setVisible(false);
				lblerror_22.setVisible(false);
				lblerror_23.setVisible(false);
				switchPanels(lpFeatures, pnlRemoveMember);
			}
		});
		btnRemoveMember.setBounds(413, 158, 89, 23);
		pnlFeatureButtons.add(btnRemoveMember);

		btnStudentProfle = new JButton(
				"\u041F\u0440\u043E\u0444\u0438\u043B\u044C \u0441\u0442\u0443\u0434\u0435\u043D\u0442\u0430");
		btnStudentProfle.setForeground(Color.YELLOW);
		btnStudentProfle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbSupervisor.setEnabled(false);
				cbSelectTS.setSelectedIndex(0);
				lblerror_40.setVisible(false);
				fldMail_Tutor.setEditable(true);
				fldPassport.setText("XXXX XXXXXX");
				fldPassport.setForeground(Color.LIGHT_GRAY);
				fldFIO.setText("Иванов Иван Иванович");
				fldFIO.setForeground(Color.LIGHT_GRAY);
				switchPanels(lpFeatures, pnlStudentProfile);
			}
		});
		btnStudentProfle.setBounds(121, 204, 89, 23);
		pnlFeatureButtons.add(btnStudentProfle);

		btnCreateCabinet = new JButton(
				"\u0421\u043E\u0437\u0434\u0430\u0442\u044C \u043A\u0430\u0431\u0438\u043D\u0435\u0442");
		btnCreateCabinet.setForeground(Color.GREEN);
		btnCreateCabinet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblerror_9.setVisible(false);
				switchPanels(lpFeatures, pnlCreateCabinet);
			}
		});
		btnCreateCabinet.setBounds(314, 204, 89, 23);
		pnlFeatureButtons.add(btnCreateCabinet);

		btnExit = new JButton("\u0412\u044B\u0445\u043E\u0434");
		btnExit.setForeground(Color.RED);
		btnExit.setBounds(413, 204, 89, 23);
		pnlFeatureButtons.add(btnExit);

		btnGetPasswords = new JButton("\u0412\u044B\u0434\u0430\u0442\u044C \u043F\u0430\u0440\u043E\u043B\u0438");
		btnGetPasswords.setForeground(Color.YELLOW);
		btnGetPasswords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO положение таблицы
				// A15_get_passwords(OUT result_status TINYINT)
				String procedure = "A15_get_passwords";
				try {
					ResultSet response = db.request(procedure, null, null, null);
					JTable table = fill_table(response);
					table.setEnabled(false);
					spPasswords.setViewportView(table);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				switchPanels(lpFeatures, pnlPasswords);
			}
		});
		btnGetPasswords.setBounds(512, 38, 89, 23);
		pnlFeatureButtons.add(btnGetPasswords);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
				switchPanels(lpMain, pnlLogin);
			}
		});

	}

	protected JTable fill_table(ResultSet response) throws SQLException {
		String headers[] = db.getHeaders(response);
		String content[][] = db.getContent(response);
		JTable table = new JTable(content, headers);
		setSize(table, headers, content);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return table;
	}

	protected JTable fill_profile(ResultSet response) throws SQLException {
		String params[] = db.getHeaders(response);
		String values[][] = db.getContent(response);
		String content[][] = new String[params.length][2];
		for (int i = 0; i < params.length; i++) {
			content[i][0] = params[i];
			content[i][1] = values[0][i];
		}
		String headers[] = { "Пареметр", "Значение" };
		JTable table = new JTable(content, headers);
		setSize(table, headers, content);
		return table;
	}

	protected void setSize(JTable table, String headers[], String content[][]) {
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < headers.length; i++) {
			int weight1 = headers[i].length();
			int weight2 = content[0][i].length();
			for (int j = 0; j < content.length; j++) {
				weight2 = Math.max(weight2, content[j][i].length());
			}
			int weight = Math.max(weight1, weight2);
			columnModel.getColumn(i).setPreferredWidth(weight * 8);
		}
	}
}
