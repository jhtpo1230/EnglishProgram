package EnlgishProgram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class mainFrame extends JFrame {
	JLabel title = new JLabel("���� ���� ȭ����!", SwingConstants.CENTER);
	JPanel fileaddPanel = new JPanel();
	JButton fileaddButton = new JButton("���� �߰��ϱ�");
	JButton wordaddButton = new JButton("�ܾ� �߰��ϱ�");
	JPanel startPanel = new JPanel();
	JPanel testStartPanel = new JPanel();
	JButton studyStart = new JButton("���� ��������");
	JButton testStart = new JButton("���� ��������");
	JPanel fileSelectPanel = new JPanel();
	JButton select = new JButton("���� �����ϱ�");
	JLabel fileInformation = new JLabel("���õ� ���� : ");
	int selecting = 0;
	int AddWordNum = 0;
	int TestNum = 1;
	boolean TestFinish = false;

	JTextArea memozang = new JTextArea(20, 10);

	String fileNameString;
	JFileChooser fc = new JFileChooser();;

	class word {
		private String eng;
		private String kor;

		public word(String eng, String kor) {
			this.eng = eng;
			this.kor = kor;
		}

		public String getEng() {
			return eng;
		}

		public String getKor() {
			return kor;
		}
	}

	Vector<word> v = new Vector<word>();

	class wordList {

		public wordList() {
			File file = new File("C:\\Users\\a\\Desktop\\�ڹ�2\\���ܾ� ���α׷�\\���ܾ� ���α׷� �ܾ���\\" + fileNameString);

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				String line;
				int VectorNum = 0;
				ArrayList<String> EngStr = new ArrayList<>();
				ArrayList<String> KorStr = new ArrayList<>();

				while ((line = br.readLine()) != null) {
					String[] splitStr = line.split("\\s+");
					VectorNum++;
					EngStr.add(splitStr[0]);
					KorStr.add(splitStr[1]);
				}

				int m, n;
				int RandomWordArray[] = new int[VectorNum]; // �ܾ� ���� ����� ���� ���� ���� ���� �迭
				Random random = new Random();

				for (m = 0; m < VectorNum; m++) { // �ܾ �� ���� �� �ֵ��� �ߺ� ����
					RandomWordArray[m] = random.nextInt(VectorNum);
					for (n = 0; n < m; n++) {
						if (RandomWordArray[m] == RandomWordArray[n]) {
							m--;
						}
					}
				}
				for (m = 0; m < VectorNum; m++) {
					v.add(new word(EngStr.get(RandomWordArray[m]), KorStr.get(RandomWordArray[m])));
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class finalTest extends JFrame implements ActionListener {
		public int num = 5; // �ܾ� ����
		public int test = 0; // testSystem ��ư�� ���� ��ȭ�ϴ� ����

		String[] data = new String[num]; // ���� ���� ���� ������ data �迭�� ���������� ���� => �����Ʈ�� ����ϱ� ���� ����

		JButton B_1 = new JButton("     ä��     ");
		JButton B_2 = new JButton(" �����Ʈ ");
		JButton B_3 = new JButton("   �����   ");

		JButton SaveMemo = new JButton("�޸��� �����ϱ�");

		JLabel score = new JLabel();

		JButton backButton = new JButton("���ư���");
		JButton exitProgram = new JButton("�����ϱ�");

		public finalTest() {
			setTitle("���ܾ� �ϱ� ���α׷�");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 800);
			setLayout(new GridLayout(0, 2));

			class WordPanel extends JPanel implements ActionListener {

				JPanel[] OneWordpanel = new JPanel[num];

				wordList WL = new wordList();

				JLabel[] korWord = new JLabel[num];
				JTextField[] engWord = new JTextField[num];

				int i, j;
				int a[] = new int[num]; // �ܾ� ���� ����� ���� ���� ���� ���� �迭
				Random random = new Random();

				public WordPanel() {

					for (i = 0; i < num; i++) {
						OneWordpanel[i] = new JPanel();
						OneWordpanel[i].setLayout(new GridLayout(0, 2));
						OneWordpanel[i].add(korWord[i] = new JLabel(v.get(i).getKor()), BorderLayout.CENTER);
						OneWordpanel[i].add(engWord[i] = new JTextField());
					}
					for (i = 0; i < num; i++) { // �ܾ �� ���� �� �ֵ��� �ߺ� ����
						a[i] = random.nextInt(num);
						for (j = 0; j < i; j++) {
							if (a[i] == a[j]) {
								i--;
							}
						}
					}
					for (i = 0; i < num; i++) {
						this.add(OneWordpanel[a[i]]);
						// OneWordPanel�� 30���� �ܾ� ������� ����, ��¸� �迭 �ε��� ���� ���� �г� ���� ���
					}
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					int correctNum = 0;
					int wrongNum = 0;

					if (e.getSource() == B_1) {
						if (TestFinish == false) {
							int i;

							for (i = 0; i < num; i++) {
								data[i] = engWord[i].getText().strip(); // ���� ���� ����� data �迭�� ����

								if (data[i].equalsIgnoreCase((v.get(i)).getEng()) == true) {
									correctNum++;
								}
							}
							wrongNum = num - correctNum;
							String[] wrong = new String[wrongNum]; // Ʋ�� ���� ������ wrong �迭
							for (i = 0; i < wrongNum; i++) {
								if (data[i].equalsIgnoreCase((v.get(i)).getEng()) != true) {
									wrong[i] = data[i];
								}
							}

							double result = (double) correctNum / (double) num * 100;
							String RealScore = String.format("%.1f", result);

							score.setText(RealScore + "��");
							TestFinish = true;

							if (result >= 80)
								test = 2; // �հ�
							else
								test = 1; // ���հ�
						} else if (TestFinish == true) {
							JOptionPane notice = new JOptionPane();
							notice.showMessageDialog(null, "ä���� ��������Ƿ� ������Ͻʽÿ�.");
						}

					} else if (e.getSource() == B_3) { // ������� ��������
						if (TestNum <= 5) {
							TestFinish = false;

							if (test == 0) { // ä���� �� �� ���
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "ä���� ���� �����Ͻʽÿ�.");
							} else if (test == 2) { // ä�� ����� �հ��� ���
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "�հ��Դϴ�.");
							} else if (test == 1) { // ä�� ����� ���հ��� ���

								for (i = 0; i < num; i++) {
									this.remove(OneWordpanel[i]);
								}
								num--;

								score.setText("����");

								for (i = 0; i < num; i++) {
									OneWordpanel[i] = new JPanel();
									OneWordpanel[i].setLayout(new GridLayout(0, 2));
									OneWordpanel[i].add(korWord[i] = new JLabel(v.get(i).getKor()),
											BorderLayout.CENTER);
									OneWordpanel[i].add(engWord[i] = new JTextField());
								}
								for (i = 0; i < num; i++) { // �ܾ �� ���� �� �ֵ��� �ߺ� ����
									a[i] = random.nextInt(num);
									for (j = 0; j < i; j++) {
										if (a[i] == a[j]) {
											i--;
										}
									}
								}
								for (i = 0; i < num; i++) {
									this.add(OneWordpanel[a[i]]);
									// OneWordPanel�� 30���� �ܾ� ������� ����, ��¸� �迭 �ε��� ���� ���� �г� ���� ���
								}
								test = 0;
								TestNum++;
							}
						} else if (TestNum > 4) { // ��ȸ�� 5��
							JOptionPane notice = new JOptionPane();
							notice.showMessageDialog(null, "��� ��ȸ�� �������ϴ�." + "�ٽ� �����Ͻʽÿ�.");
							dispose();
							new mainFrame();
						}
					}
				}

			} // wordPanel class

			WordPanel WordPanel = new WordPanel();
			WordPanel.setLayout(new GridLayout(0, 1));

			JPanel systemPanel = new JPanel();

			JPanel buttonPanel = new JPanel();
			buttonPanel.setSize(0, 400);
			buttonPanel.setLayout(new GridLayout(3, 1));
			JPanel b_Panel_1 = new JPanel();
			b_Panel_1.add(B_1);
			B_1.addActionListener(WordPanel);
			JPanel b_Panel_2 = new JPanel();
			b_Panel_2.add(B_2);
			B_2.addActionListener(this);
			JPanel b_Panel_3 = new JPanel();
			b_Panel_3.add(B_3);
			B_3.addActionListener(WordPanel);

			buttonPanel.add(b_Panel_1, BorderLayout.CENTER);
			buttonPanel.add(b_Panel_2, BorderLayout.CENTER);
			buttonPanel.add(b_Panel_3, BorderLayout.CENTER);
			systemPanel.add(buttonPanel, BorderLayout.NORTH);

			JPanel memoPanel = new JPanel();
			JLabel memo = new JLabel("�޸���");
			memoPanel.add(memo);
			memozang.setLineWrap(true); // ���� �ڵ� �ѱ��
			JScrollPane scrollPanel = new JScrollPane(memozang, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // ��ũ��

			memoPanel.add(scrollPanel);
			systemPanel.add(memoPanel);

			JPanel SaveMemoPanel = new JPanel();
			SaveMemoPanel.add(SaveMemo);
			SaveMemo.addActionListener(this);
			systemPanel.add(SaveMemoPanel);

			JPanel scorePanel = new JPanel();
			score = new JLabel("����");
			score.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 85));
			scorePanel.add(score);
			systemPanel.add(scorePanel, BorderLayout.SOUTH);

			JPanel conclusionPanel = new JPanel();
			backButton.addActionListener(this);
			exitProgram.addActionListener(this);
			conclusionPanel.add(backButton);
			conclusionPanel.add(exitProgram);
			systemPanel.add(conclusionPanel, BorderLayout.SOUTH);

			add(WordPanel);
			add(systemPanel);

			this.setResizable(false); // JFrame ũ�� ����
			this.setLocationRelativeTo(null); // JFrame ��ġ ��� ����

			setVisible(true);
		}

		wrongNote wrongNote;

		class wrongNote extends JDialog {
			JPanel[] WrongNotePanel = new JPanel[num];

			JLabel KorWordLabel = new JLabel();
			JLabel EngWordLabel = new JLabel();

			public wrongNote(String string) {
				JPanel note = new JPanel();

				for (int i = 0; i < num; i++) {
					WrongNotePanel[i] = new JPanel();
					WrongNotePanel[i].setLayout(new GridLayout(0, 3));
					WrongNotePanel[i].add(KorWordLabel = new JLabel(v.get(i).getKor())); // ��
					WrongNotePanel[i].add(EngWordLabel = new JLabel(v.get(i).getEng())); // ���ܾ�
					if (data[i].equalsIgnoreCase(v.get(i).getEng()) == true) { // �� ����� �����Ͻ�
						JLabel MyWordLabel = new JLabel(data[i]); // �� ����� ���� ��
						MyWordLabel.setForeground(Color.BLUE); // ������ �Ķ�
						WrongNotePanel[i].add(MyWordLabel);
					} else if (data[i].equalsIgnoreCase(v.get(i).getEng()) == false) { // �� ����� �����Ͻ�
						JLabel MyWordLabel = new JLabel(data[i]);
						MyWordLabel.setForeground(Color.RED); // ������ ����
						WrongNotePanel[i].add(MyWordLabel);
					}
					note.add(WrongNotePanel[i]);
				}
				note.setLayout(new GridLayout(0, 1));
				this.add(note);
				this.setSize(400, 800);
				this.setLocationRelativeTo(null); // â ������ ���
				this.setTitle("�����Ʈ");
				this.setModal(false); // �����Ʈ�� �ѵ� ���� â ���� ���� (�θ� ������ ��� ����)
				this.setVisible(true);
			}
		}

		class MemoAddFrame extends JDialog implements ActionListener {
			JPanel MemoNamePanel = new JPanel();
			JLabel MemoName = new JLabel("������ �޸��� �̸� : ");
			JTextField MemoNameWrite = new JTextField(10);
			JPanel MemoAddPanel = new JPanel();
			JButton MemoAdd = new JButton("����");

			public MemoAddFrame() {
				this.setTitle("�޸��� �����ϱ�");
				MemoNamePanel.add(MemoName);
				MemoNamePanel.add(MemoNameWrite);
				MemoAddPanel.add(MemoAdd);
				MemoAdd.addActionListener(this);
				MemoNamePanel.add(MemoAddPanel);

				getContentPane().add(MemoNamePanel);

				this.setSize(200, 150);
				this.setModal(true);
				this.setVisible(true);

			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == MemoAdd) {
					String MemoNameString = MemoNameWrite.getText();
					FileWriter memoWriter = null;

					try {
						File Memofile = new File(
								"C:\\Users\\a\\Desktop\\�ڹ�2\\���ܾ� ���α׷�\\���ܾ� ���� �޸���\\" + MemoNameString + ".txt");
						try {
							memoWriter = new FileWriter(
									"C:\\Users\\a\\Desktop\\�ڹ�2\\���ܾ� ���α׷�\\���ܾ� ���� �޸���\\" + MemoNameString + ".txt",
									true);
							PrintWriter printWriter = new PrintWriter(memoWriter);
							printWriter.print(memozang.getText());

							JOptionPane notice = new JOptionPane();
							notice.showMessageDialog(null, "�޸��� ���� �Ϸ�: " + MemoNameString + ".txt");

							printWriter.close();

						} catch (Exception e1) {
							e1.getStackTrace();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					dispose();
				}
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == B_2) { // �����Ʈ�� ��������
				if (test == 0) { // ä���� �� �� ���
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "ä���� ���� �����Ͻʽÿ�.");
				} else {
					wrongNote = new wrongNote(e.getActionCommand());
				}
			} else if (e.getSource() == B_3) { // ������� ��������
				if (test == 0) { // ä���� �� �� ���
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "ä���� ���� �����Ͻʽÿ�.");
				} else if (test == 2) { // ä�� ����� �հ��� ���
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "�հ��Դϴ�.");
				}
			} else if (e.getSource() == SaveMemo) {
				if ((memozang.getText()).equals(null)) {
					JOptionPane notice = new JOptionPane();
					notice.showMessageDialog(null, "�޸��忡 ������ ������ �Է��Ͻÿ�");
				} else {
					new MemoAddFrame();
				}
			} else if (e.getSource() == backButton) {
				this.dispose();
				new mainFrame();
			} else if (e.getSource() == exitProgram) {
				JOptionPane notice = new JOptionPane();
				notice.showMessageDialog(null, "����ϼ̽��ϴ�");
				this.dispose();
			}
		}
	}

	int studyTime = 1;

	class studyNote extends JDialog { // �ܾ �������� ��ũ���� ����� ��

		JLabel KorWordStudyLabel;
		JLabel EngWordStudyLabel;

		public studyNote() throws IOException {

			if (studyTime == 1) {
				new wordList();
			}

			JPanel studyNoteWholePanel = new JPanel();
			JPanel[] studyNotePanel = new JPanel[(v.size()) + AddWordNum];
			System.out.println(v.size());
			System.out.println(studyTime);
			System.out.println(AddWordNum);

			studyNoteWholePanel.setLayout(new GridLayout(0, 1));

			File fileForStudy = new File("C:\\Users\\a\\Desktop\\�ڹ�2\\���ܾ� ���α׷�\\���ܾ� ���α׷� �ܾ���\\" + fileNameString);
			ArrayList<String> EngStr = new ArrayList<>();
			ArrayList<String> KorStr = new ArrayList<>();

			try (BufferedReader studyBr = new BufferedReader(new FileReader(fileForStudy))) {

				String line;

				while ((line = studyBr.readLine()) != null) {
					String[] splitStr = line.split("\\s+");
					EngStr.add(splitStr[0]);
					KorStr.add(splitStr[1]);
				}
			}

			for (int i = 0; i < (v.size() + AddWordNum); i++) {
				studyNotePanel[i] = new JPanel();
				studyNotePanel[i].setLayout(new GridLayout(0, 2));
				studyNotePanel[i].add(KorWordStudyLabel = new JLabel(KorStr.get(i))); // ��
				studyNotePanel[i].add(EngWordStudyLabel = new JLabel(EngStr.get(i))); // ���ܾ�
				EngWordStudyLabel.setForeground(Color.BLUE);
				studyNoteWholePanel.add(studyNotePanel[i]);
			}

			this.add(studyNoteWholePanel);
			this.setSize(400, 800);
			this.setLocationRelativeTo(null); // â ������ ���
			this.setTitle("���γ�Ʈ");
			this.setModal(false); // ���γ�Ʈ�� �ѵ� ���� â ���� ���� (�θ� ������ ��� ����)
			this.setVisible(true);
		}
	}

	public mainFrame() {
		setTitle("���ܾ� �ϱ� ���α׷�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);

		class myPanel extends JPanel implements ActionListener {

			public myPanel() {
				setLayout(new GridLayout(2, 0));
				add(title);
				title.setFont(new Font("���� ���", Font.BOLD, 20));
				startPanel.setLayout(new GridLayout(4, 0));
				fileaddPanel.add(fileaddButton);
				fileaddButton.addActionListener(this);
				fileaddPanel.add(wordaddButton);
				wordaddButton.addActionListener(this);
				startPanel.add(fileaddPanel, BorderLayout.CENTER);
				fileSelectPanel.add(select);
				startPanel.add(fileSelectPanel, BorderLayout.CENTER);
				select.addActionListener(this);
				fileInformation.setOpaque(true);
				fileInformation.setBackground(Color.WHITE);
				startPanel.add(fileInformation);
				testStartPanel.add(studyStart);
				testStartPanel.add(testStart);
				startPanel.add(testStartPanel, BorderLayout.CENTER);
				studyStart.addActionListener(this);
				testStart.addActionListener(this);
				add(startPanel);

			}

			class fileAddFrame extends JDialog implements ActionListener {
				JPanel fileNamePanel = new JPanel();
				JLabel fileName = new JLabel("������ ���� �̸� : ");
				JTextField fileNameWrite = new JTextField(10);
				JPanel fileAddPanel = new JPanel();
				JButton fileAdd = new JButton("����");

				public fileAddFrame() {
					this.setTitle("���ܾ� ���� �߰��ϱ�");
					fileNamePanel.add(fileName);
					fileNamePanel.add(fileNameWrite);
					fileAddPanel.add(fileAdd);
					fileAdd.addActionListener(this);
					fileNamePanel.add(fileAddPanel);

					getContentPane().add(fileNamePanel);

					this.setSize(200, 150);
					this.setModal(true);
					this.setVisible(true);

				}

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == fileAdd) {
						fileNameString = fileNameWrite.getText();
						// ���� ����
						try {
							// ���� ��ü ����
							File file = new File(
									"C:\\Users\\a\\Desktop\\�ڹ�2\\���ܾ� ���α׷�\\���ܾ� ���α׷� �ܾ���\\" + fileNameString + ".txt");
							if (file.createNewFile()) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "���� ���� �Ϸ�: " + file.getName());
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						dispose();
					}
				}
			}

			class wordAddFrame extends JDialog implements ActionListener {

				JLabel fileInfor = new JLabel("null");

				JPanel WordAddButtonPanel = new JPanel();
				JButton WordAdd = new JButton("�߰�");

				JPanel WordAddSystemPanel = new JPanel();

				JPanel EngAddPanel = new JPanel();
				JLabel EngAddLabel = new JLabel("���ܾ� : ");
				JTextField EngAddField = new JTextField(15);

				JPanel KorAddPanel = new JPanel();
				JLabel KorAddLabel = new JLabel("�� : ");
				JTextField KorAddField = new JTextField(15);

				JPanel WordAddWholePanel = new JPanel();

				public wordAddFrame() {
					this.setTitle("���ܾ� �߰��ϱ�");
					WordAddWholePanel.setLayout(new GridLayout(3, 0));
					WordAddWholePanel.add(fileInfor);
					if (selecting == 0) {
						JOptionPane notice = new JOptionPane();
						notice.showMessageDialog(null, "������ �����Ͻʽÿ�.");
					} else if (selecting == 1) {
						fileInfor.setText(fileInformation.getText());
						WordAddSystemPanel.setLayout(new GridLayout(2, 0));

						EngAddPanel.add(EngAddLabel);
						EngAddPanel.add(EngAddField);
						WordAddSystemPanel.add(EngAddPanel);

						KorAddPanel.add(KorAddLabel);
						KorAddPanel.add(KorAddField);
						WordAddSystemPanel.add(KorAddPanel);

						WordAddWholePanel.add(WordAddSystemPanel);
						WordAddButtonPanel.add(WordAdd);
						WordAdd.addActionListener(this);
						WordAddWholePanel.add(WordAddButtonPanel);

						getContentPane().add(WordAddWholePanel);

						this.setSize(400, 200);
						this.setModal(true);
						this.setVisible(true);
					}
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					// �ܾ� �߰�
					if (e.getSource() == WordAdd) {
						// ���� ��ġ : fileInformation
						// ���� : EngAddField , �� : KorAddField
						FileWriter fileWriter = null;

						if (Pattern.matches("^[��-����-�R]*$", KorAddField.getText()) != true
								|| Pattern.matches("^[a-zA-Z]*$", EngAddField.getText()) != true) {
							if (Pattern.matches("^[��-����-�R]*$", KorAddField.getText()) == false
									&& Pattern.matches("^[a-zA-Z]*$", EngAddField.getText()) == false) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "���ܾ �Է��ҽ� ���, " + "\n" + "���� �Է��ҽ� �ѱ۸� �Է��Ͻÿ�.");
							} else if (Pattern.matches("^[��-����-�R]*$", KorAddField.getText()) == false) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "���� �Է��ҽ� �ѱ۸� �Է��Ͻÿ�.");
							} else if (Pattern.matches("^[a-zA-Z]*$", EngAddField.getText()) == false) {
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, "���ܾ �Է��ҽ� ��� �Է��Ͻÿ�.");
							}
						} else {
							try {
								fileWriter = new FileWriter(
										"C:\\Users\\a\\Desktop\\�ڹ�2\\���ܾ� ���α׷�\\���ܾ� ���α׷� �ܾ���\\" + fileNameString, true);
								PrintWriter printWriter = new PrintWriter(fileWriter);
								printWriter.print(EngAddField.getText() + " ");
								printWriter.print(KorAddField.getText());
								printWriter.print("\n");
								JOptionPane notice = new JOptionPane();
								notice.showMessageDialog(null, EngAddField.getText() + "�� �߰��Ǿ����ϴ�.");
								AddWordNum++;
								EngAddField.setText(null);
								KorAddField.setText(null);
								printWriter.flush();

							} catch (Exception e1) {
								e1.getStackTrace();
							}
						}
					}

				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == fileaddButton) {
					new fileAddFrame();

				} else if (e.getSource() == select) {
					int returnVal = fc.showOpenDialog(this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						try {
							fileInformation.setText("���õ� ���� : " + file.getName());
							fileNameString = file.getName();
							selecting = 1;
						} catch (Exception e1) {

						}
					}
				} else if (e.getSource() == wordaddButton) {
					new wordAddFrame();
				} else if (e.getSource() == studyStart) {
					if (selecting == 0) {
						JOptionPane notice = new JOptionPane();
						notice.showMessageDialog(null, "������ �����Ͻʽÿ�.");
					} else if (selecting == 1) {
						try {
							new studyNote();
							studyTime++;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

				else if (e.getSource() == testStart) {
					if (selecting == 0) {
						JOptionPane notice = new JOptionPane();
						notice.showMessageDialog(null, "������ �����Ͻʽÿ�.");
					} else if (selecting == 1) {
						dispose(); // â ���ֱ�
						new finalTest();
					}
				}
			}
		}

		JPanel myPanel = new myPanel();

		this.add(myPanel);
		this.setResizable(false); // JFrame ũ�� ����
		setVisible(true);
	}

	public static void main(String[] args) {
		new mainFrame();
	}
}
