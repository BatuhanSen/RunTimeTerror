class todo extends Component {
    state={
        items:[],
        id:uuid(),
        item:'',
        status:'Active',
        editItem:false,
    };

    componentDidMount() {
        let data = new FormData();
        data.append('getList', true);

        fetch(('http://localhost/test/todo.php'), {
            method: 'POST',
            body: data,
            header:  {

            },
        }).then((response) => response.json())
            .then(response =>  {
                let datas = response.items;
                datas.map(data => {
                    if (data.task_title !== ""){
                        const newItem = {
                            title:data.task_title,
                            id: data.task_id,
                            status: data.task_status
                        };
                        const updatedItems = [...this.state.items, newItem];

                        this.setState({
                            items:updatedItems,
                            item:'',
                            id:uuid(),
                            editItem:false,
                            status: true
                        });
                    }

                })
            });
    }

    handleChange = e => {
        this.setState({
            item:e.target.value
        });
    };
    handleSubmit = e => {
        e.preventDefault();
      if (this.state.item !== "")  {
          const newItem = {
              id:this.state.id,
              title:this.state.item,
              status:this.state.status
          };
          const updatedItems = [...this.state.items, newItem];
          this.setState({
              items:updatedItems,
              item:'',
              id:uuid(),
              status:'Active',
              editItem:false
          });


          let formData;
          formData = new FormData();
          formData.append('content', newItem.title);
          formData.append('id', newItem.id);
          formData.append('status', newItem.status);
          formData.append('edit', this.state.editItem);

          fetch(('http://localhost/test/todo.php'), {
              method:'POST',
              body: formData,
              header:{
                  //'Accept' : 'application/json',
                  //'Content-type' : 'application/json'
              },
          }).then((response)=> response.json())
              .then(response => {
              })
      }
    };
    clearList = () => {
        this.setState({
            items: []
        });

      let data = new FormData();
        data.append('clear', 'clear');

      fetch(('http://localhost/test/todo.php'), {
          method:'POST',
          header:{

    },
        body: data,
    }).then((response) => response.json())

    };
    handleDelete = (id) => {
        const filteredItems = this.state.items.filter(item => item.id !== id);
        this.setState({
            items: filteredItems
        });

      let data = new FormData();
        data.append('del_id', id);

      fetch(('http://localhost/test/todo.php'), {
          method:'POST',
          header:{
              //'Accept' : 'application/json',
              //'Content-type' : 'application/json'
          },
          body: data,
      }).then((response)=> response.json())
          .then(response => {

          });

    };
    handleEdit = (id) => {
        const filteredItems = this.state.items.filter(item => item.id !== id);
        const selectedItem = this.state.items.find(item => item.id === id);

      this.setState({
          items:filteredItems,
          item: selectedItem.title,
          editItem: true,
          id: selectedItem.id
      });

      let formData = new FormData();
        formData.append('content', selectedItem.title);
        formData.append('id', selectedItem.id);
        formData.append('edit', this.state.editItem);

      fetch(('http://localhost/test/todo.php'), {
          method:'POST',
          header:{
              //'Accept' : 'application/json',
              //'Content-type' : 'application/json'
          },
          body: formData,
      }).then((response)=> response.json())
          .then(response => {
              //console.log('response:');
              //console.log(response);
              if(response.success) {
                  console.log('Islem basarılı listeye ekle')
              } else {
                  console.log('Hata var');
              }
          })

    };
    handleActive = (id) => {
        let itemList = [...this.state.items];
        let currentItem = itemList.find(x => x.id === id);
        currentItem.status = 'Active';
        this.setState({
            items: itemList
        });

        let formData = new FormData();
        formData.append('comp_id', currentItem.id);
        fetch(('http://localhost/test/todo.php'), {
            method:'POST',
            header:{
                //'Accept' : 'application/json',
                //'Content-type' : 'application/json'
            },
            body: formData,
        }).then((response)=> response.json())
            .then(response => {
                if (response.success)
                    this.setState({
                        status:'Active',
                    })
            });
    };
    handleComplete = (id) => {
        console.log('ıd', id, this.state.items);
        let itemList = [... this.state.items];
        let currentItem = itemList.find(x => x.id === id);
        currentItem.status = 'Completed';
        this.setState({
            items: itemList
        });

        let formData = new FormData();
        formData.append('comp_id', currentItem.id);
        fetch(('http://localhost/test/todo.php'), {
            method:'POST',
            header:{
                //'Accept' : 'application/json',
                //'Content-type' : 'application/json'
            },
            body: formData,
        }).then((response)=> response.json())
            .then(response => {
                if (response.success)
                    this.setState({
                        status: 'Completed',
                    })
            });
    };

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-10 mx-auto col-md-8 mt-4">
                        <h1 className="text-capitalize text-center text-white">super hero todo List</h1>
                        <ToDoInput
                            item={this.state.item}
                            handleChange = {this.handleChange}
                            handleSubmit = {this.handleSubmit}
                            editItem = {this.state.editItem}
                        />
                        <ToDoList
                            items = {this.state.items}
                            clearList = {this.clearList}
                            handleDelete = {this.handleDelete}
                            handleEdit = {this.handleEdit}
                            handleActive = {this.handleActive}
                            handleComplete = {this.handleComplete}
                            />
                    </div>
                </div>
            </div>
        );
    }
}
export default todo;