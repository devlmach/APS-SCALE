# 🌱 Sistema de Cálculo de Emergia (SCALE - Java)

Este projeto consiste no desenvolvimento de um sistema computacional para cálculo de emergia, inspirado no software **SCALE (Software for Calculating Algebraic Emergy)**.

O sistema permite a integração de dados de Inventário de Ciclo de Vida (LCI) com a análise emergética, aplicando conceitos fundamentais da Engenharia de Software e da sustentabilidade ambiental.

---

## 📌 Objetivo

Automatizar o cálculo de emergia a partir de dados de entrada, garantindo:

- Aplicação correta da fórmula:  
  **Emergia = Quantidade × UEV**
- Organização dos dados por recursos e processos
- Evitar erros de cálculo manual
- Apoiar análises de sustentabilidade

---

## 🚀 Funcionalidades

- 📥 Importação de dados via CSV (LCI)
- ✍️ Cadastro manual de recursos
- ⚙️ Cálculo automático de emergia
- 📊 Separação por tipo:
    - R (Renovável)
    - NR (Não Renovável)
    - N (Outros)
- 🖥️ Interface gráfica interativa (Java Swing)
- 📄 Geração de relatório em `.txt`
- 🔄 Estrutura baseada em processos (modelo SCALE simplificado)

---

## 🧱 Estrutura do Projeto

```
APS.7.SEMESTRE
├── src
│   ├── Model.Recurso.java
│   ├── Model.Processo.java
│   ├── Model.CalculadoraEmergia.java
│   └── View.SistemaEmergiaGUI.java
├── dados
│   ├── base_completa_epa.csv
│   ├── base_industrial.csv
│   └── base_teste_rapido.csv
