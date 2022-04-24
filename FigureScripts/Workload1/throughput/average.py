import os
import plotly.graph_objects as go
import plotly.io as pio
import plotly.express as px

pio.kaleido.scope.mathjax = None


fig = go.Figure()

fig.add_trace(go.Bar(name="Desis", x=[" "], y=[450], legendrank=2, marker_color='rgb(144,170,219)', width=[0.3]))
fig.add_trace(go.Bar(name="Centralized", x=[" "], y=[86], legendrank=1, marker_color='rgb(168,208,141)', width=[0.3]))
# fig.update_traces(marker_color='rgb(158,202,225)', marker_line_color='rgb(8,48,107)', marker_line_width=1.5, opacity=0.6)

#legend
fig.update_layout(
    legend=dict(
        yanchor="top",
        y=0.99,
        xanchor="left",
        x=0.01,
        # bordercolor="Black",
        # borderwidth=2,
        # bgcolor="white",
        font=dict(
            size=10,
            color="black"
        ),
    ),
    yaxis=dict(
        title_text="tuples/sec",
        titlefont=dict(size=10),
        ticktext=["0", "100", "200", "300", "400", "500"],
        tickvals=[0, 100, 200, 300, 400, 500],
        tickmode="array",
    )
)

# size & color
fig.update_layout(
    autosize=False,
    width=350,
    height=500,
    paper_bgcolor='rgba(0,0,0,0)',
    plot_bgcolor='rgba(0,0,0,0)'
)
# fig = px.bar(x=["a","b","c"], y=[1,3,2], color=["red", "goldenrod", "#00D"], color_discrete_map="identity")
fig.update_layout(barmode='group', bargap=0.35,bargroupgap=0.0)

# fig.update_yaxes(automargin=True)
fig.update_yaxes(ticks="outside", tickwidth=1, tickcolor='black', ticklen=5)
fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror=True)
fig.update_yaxes(range=[0,600], showline=True, linewidth=1, linecolor='black', mirror=True)



fig.show()
if not os.path.exists("E:\My Paper\DesisPaper\experiment"):
    os.mkdir("E:\My Paper\DesisPaper\experiment")
# fig.write_image("images/fig1.svg")
pio.write_image(fig, "E:\My Paper\DesisPaper\experiment/workload1ThroughputheadAverage.pdf")