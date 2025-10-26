import 'package:flutter/material.dart';

class GameScreen extends StatelessWidget {
  final List<String> questions = [
    'Truth: Apa rahasiamu?',
    'Dare: Lakukan 10 push-up',
    'Truth: Siapa crush-mu?',
    'Dare: Joget selama 15 detik',
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Game Time!')),
      body: Center(
        child: ListView.builder(
          itemCount: questions.length,
          itemBuilder: (context, index) {
            return Card(
              margin: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              elevation: 8,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20),
              ),
              child: ListTile(
                title: Text(
                  questions[index],
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
                trailing: Icon(Icons.play_arrow, color: Colors.purple),
              ),
            );
          },
        ),
      ),
    );
  }
}
